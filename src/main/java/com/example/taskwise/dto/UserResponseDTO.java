package com.example.taskwise.dto;

import com.example.taskwise.model.Role;
import com.example.taskwise.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<RoleDTO> roles =new ArrayList<>();

    public  UserResponseDTO() {

    }

    public UserResponseDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserResponseDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();

        for (Role role : entity.getRoles()) {
            roles.add(new RoleDTO(role));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }
}
