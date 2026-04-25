package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserResponsePrinter {

  private static final String SEPARATOR  = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-10s : %s%n";
  private static final String NO_USERS   = "  No users found.";
  private static final String UNKNOWN_STATUS = "Estado desconocido";

  // Regla 16: cadena if/else reemplazada por Map de estados a etiquetas
  private static final Map<String, String> STATUS_LABELS = Map.of(
          "ACTIVE",   "Activo",
          "INACTIVE", "Inactivo",
          "PENDING",  "Pendiente de activacion",
          "BLOCKED",  "Bloqueado",
          "DELETED",  "Eliminado");

  private final ConsoleIO console;

  public void print(final UserResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID",     response.getId());
    console.printf(ROW_FORMAT, "Name",   response.getName());
    console.printf(ROW_FORMAT, "Email",  response.getEmail());
    console.printf(ROW_FORMAT, "Role",   response.getRole());
    console.printf(ROW_FORMAT, "Status", getStatusLabel(response.getStatus()));
    console.println(SEPARATOR);
  }

  public void printList(final List<UserResponse> users) {
    // Regla 5: protegido contra null con verificación defensiva
    if (users == null || users.isEmpty()) {
      console.println(NO_USERS);
      return;
    }
    console.printf("%n  Total: %d user(s)%n", users.size());
    users.forEach(this::print);
  }

  // Regla 27: reemplazada implementación con streams anidados por lógica clara y legible
  public void printSummary(final List<UserResponse> users) {
    if (users == null || users.isEmpty()) {
      console.println(NO_USERS);
      return;
    }
    for (final UserResponse user : users) {
      console.printf("  %s (%s)%n", user.getName(), getStatusLabel(user.getStatus()));
    }
  }

  // Regla 16: Map.getOrDefault() reemplaza la cadena if/else
  private static String getStatusLabel(final String status) {
    return STATUS_LABELS.getOrDefault(status, UNKNOWN_STATUS);
  }
}