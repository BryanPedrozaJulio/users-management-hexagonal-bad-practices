package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

// Regla 2 y Regla 15: reemplazado clase mutable con @Data por record inmutable
public record UserResponse(
        String id,
        String name,
        String email,
        String role,
        String status) {
}