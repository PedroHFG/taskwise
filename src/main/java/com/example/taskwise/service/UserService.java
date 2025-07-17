package com.example.taskwise.service;

import com.example.taskwise.dto.UserRegistrationDTO;
import com.example.taskwise.dto.UserResponseDTO;
import com.example.taskwise.exception.ResourceNotFoundException;
import com.example.taskwise.model.Role;
import com.example.taskwise.model.User;
import com.example.taskwise.projection.UserDetailsProjection;
import com.example.taskwise.repository.RoleRepository;
import com.example.taskwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Email não encontrado: " + username);
        }

        User user = new User();
        user.setEmail(result.getFirst().getUsername());
        user.setPassword(result.getFirst().getPassword());

        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    @Transactional
    public UserResponseDTO registerNewUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email " + userRegistrationDTO.getEmail() + " já está em uso");
        }
        User newUser = new User();
        copyDtoToEntity(userRegistrationDTO, newUser);

        Role defaultRole = roleRepository.findByAuthority("ROLE_USER").orElseThrow(
            () -> new ResourceNotFoundException("Role 'ROLE_USER' não foi encontrada. Por favor, assegure que ela exista."));

        newUser.addRole(defaultRole);

        newUser = userRepository.save(newUser);

        return new UserResponseDTO(newUser);
    }

    private void copyDtoToEntity(UserRegistrationDTO userRegistrationDTO, User entity) {
        entity.setName(userRegistrationDTO.getName());
        entity.setEmail(userRegistrationDTO.getEmail());
        entity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
    }

    protected User authenticated() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return userRepository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getMe() {
        User me = authenticated();
        return new UserResponseDTO(me);
    }
}
