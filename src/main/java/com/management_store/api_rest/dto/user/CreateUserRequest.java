package com.management_store.api_rest.dto.user;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
    @NotBlank(message = "El nombre es obligatorio") String name,

    @NotBlank(message = "El email es obligatorio") @Email(message = "Debe ser un email válido") String email,

    @NotBlank(message = "La contraseña es obligatoria") String password,

    @NotNull(message = "El rol es obligatorio") UUID roleId) {
}
