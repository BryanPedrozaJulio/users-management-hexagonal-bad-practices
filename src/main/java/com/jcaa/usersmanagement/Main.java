package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import java.util.Scanner;
import lombok.extern.java.Log;

// Regla 24: reemplazado slf4j por @Log de Lombok — consistente con el resto del proyecto
@Log
public final class Main {

  public static void main(final String[] args) {
    log.info("Starting Users Management System...");
    // Regla 1: responsabilidades extraídas a métodos con nombre claro
    final DependencyContainer container = buildContainer();
    try (final Scanner scanner = new Scanner(System.in)) {
      final ConsoleIO console = buildConsole(scanner);
      final UserManagementCli cli = buildCli(container, console);
      cli.start();
    }
  }

  // Regla 1: construcción del contenedor de dependencias
  private static DependencyContainer buildContainer() {
    return new DependencyContainer();
  }

  // Regla 1: construcción de la infraestructura de I/O
  private static ConsoleIO buildConsole(final Scanner scanner) {
    return new ConsoleIO(scanner, System.out);
  }

  // Regla 1: construcción del CLI
  private static UserManagementCli buildCli(
          final DependencyContainer container, final ConsoleIO console) {
    return new UserManagementCli(container.userController(), console);
  }
}