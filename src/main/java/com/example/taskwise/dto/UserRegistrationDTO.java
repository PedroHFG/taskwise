package com.example.taskwise.dto;

import com.example.taskwise.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O email deve ser um endereço de email válido")
    @Size(max = 255, message = "O email não pode exceder 255 caracteres")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    public UserRegistrationDTO() {

    }

    public UserRegistrationDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserRegistrationDTO(User entity) {
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
