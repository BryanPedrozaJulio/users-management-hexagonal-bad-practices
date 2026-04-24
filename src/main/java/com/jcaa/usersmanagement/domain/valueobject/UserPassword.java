package com.jcaa.usersmanagement.domain.valueobject;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.jcaa.usersmanagement.domain.exception.InvalidUserPasswordException;
import java.util.Objects;

public final class UserPassword {

  // Regla 10: magic numbers extraídos a constantes con nombre descriptivo
  private static final int MINIMUM_LENGTH = 8;
  private static final int BCRYPT_COST    = 12;

  private final String value;

  private UserPassword(final String value) {
    this.value = value;
  }

  public static UserPassword fromPlainText(final String plainText) {
    // Regla 4: reemplazado == null por Objects.requireNonNull()
    Objects.requireNonNull(plainText, "Password cannot be null");
    final String normalizedValue = plainText.trim();
    validateNotEmpty(normalizedValue);
    validateMinimumLength(normalizedValue);
    // Regla 10: reemplazado magic number 12 por constante BCRYPT_COST
    final String hash = BCrypt.withDefaults().hashToString(BCRYPT_COST, normalizedValue.toCharArray());
    return new UserPassword(hash);
  }

  public static UserPassword fromHash(final String hash) {
    Objects.requireNonNull(hash, "Password hash cannot be null");
    return new UserPassword(hash);
  }

  public boolean verifyPlain(final String plainText) {
    final String normalizedPlain =
            Objects.requireNonNull(plainText, "Plain password cannot be null").trim();
    final BCrypt.Result result = BCrypt.verifyer().verify(normalizedPlain.toCharArray(), value);
    return result.verified;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (!(other instanceof UserPassword userPassword)) return false; // NOSONAR
    return Objects.equals(value, userPassword.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidUserPasswordException.becauseValueIsEmpty();
    }
  }

  private static void validateMinimumLength(final String normalizedValue) {
    // Regla 10: reemplazado magic number 8 por constante MINIMUM_LENGTH
    if (normalizedValue.length() < MINIMUM_LENGTH) {
      throw InvalidUserPasswordException.becauseLengthIsTooShort(MINIMUM_LENGTH);
    }
  }

  // Regla 13 y 18: isValidFormat usa MINIMUM_LENGTH — sin duplicación
  public static boolean isValidFormat(final String password) {
    return password != null && password.length() >= MINIMUM_LENGTH;
  }
}