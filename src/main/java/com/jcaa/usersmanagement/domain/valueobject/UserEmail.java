package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUserEmailException;
import java.util.Objects;
import java.util.regex.Pattern;

public record UserEmail(String value) {

  private static final Pattern EMAIL_PATTERN =
          Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

  public UserEmail {
    final String normalizedValue =
            Objects.requireNonNull(value, "UserEmail cannot be null").trim().toLowerCase();

    // Regla 6: eliminado Logger y log de PII — el dominio no debe tener logs
    validateNotEmpty(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  // Regla 23: isValidFormat() centralizado usando EMAIL_PATTERN — sin duplicación
  public static boolean isValidFormat(final String email) {
    if (email == null || email.isBlank()) {
      return false;
    }
    return EMAIL_PATTERN.matcher(email.trim().toLowerCase()).matches();
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidUserEmailException.becauseValueIsEmpty();
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!EMAIL_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidUserEmailException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}