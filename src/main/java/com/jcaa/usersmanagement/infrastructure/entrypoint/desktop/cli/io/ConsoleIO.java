package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import java.io.PrintStream;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ConsoleIO {

  // Regla 10: mensajes extraídos a constantes con nombre descriptivo
  private static final String MSG_VALUE_CANNOT_BE_BLANK = "  Value cannot be blank. Please try again.";
  private static final String MSG_INVALID_NUMBER_INPUT  = "  Invalid input. Please enter a number.";

  private final Scanner scanner;
  private final PrintStream out;

  public String readRequired(final String prompt) {
    // Regla 4 y Regla 24: renombrado "v" → "value" — nombre descriptivo y consistente
    String value;
    do {
      out.print(prompt);
      value = scanner.nextLine().trim();
      if (value.isBlank()) {
        out.println(MSG_VALUE_CANNOT_BE_BLANK);
      }
    } while (value.isBlank());
    return value;
  }

  public String readOptional(final String prompt) {
    out.print(prompt);
    return scanner.nextLine().trim();
  }

  public int readInt(final String prompt) {
    while (true) {
      out.print(prompt);
      // Regla 4 y Regla 24: renombrado "r" → "value" — nombre descriptivo y consistente
      final String value = scanner.nextLine().trim();
      try {
        return Integer.parseInt(value);
      } catch (final NumberFormatException ignored) {
        out.println(MSG_INVALID_NUMBER_INPUT);
      }
    }
  }

  public void println(final String message) { out.println(message); }
  public void println() { out.println(); }
  public void printf(final String format, final Object... args) { out.printf(format, args); }
}