package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserEmailException extends DomainException {

  // ✅ Regla 10: textos extraídos a constantes con nombre descriptivo
  private static final String MSG_VALUE_IS_EMPTY =
          "The user email must not be empty.";
  private static final String MSG_FORMAT_IS_INVALID =
          "The user email format is invalid: '%s'.";

  private InvalidUserEmailException(final String message) {
    super(message);
  }

  public static InvalidUserEmailException becauseValueIsEmpty() {
    return new InvalidUserEmailException(MSG_VALUE_IS_EMPTY);
  }

  public static InvalidUserEmailException becauseFormatIsInvalid(final String email) {
    return new InvalidUserEmailException(String.format(MSG_FORMAT_IS_INVALID, email));
  }
}