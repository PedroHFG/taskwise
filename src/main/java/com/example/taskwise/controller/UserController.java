package com.example.taskwise.controller;

import com.example.taskwise.dto.UserRegistrationDTO;
import com.example.taskwise.dto.UserResponseDTO;
import com.example.taskwise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerNewUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserResponseDTO newUser = userService.registerNewUser(userRegistrationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> getMe() {
        UserResponseDTO me = userService.getMe();
        return ResponseEntity.ok(me);
    }
}
