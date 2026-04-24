package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserPasswordException extends DomainException {

  // Regla 10: textos extraídos a constantes con nombre descriptivo
  private static final String MSG_VALUE_IS_EMPTY =
          "The user password must not be empty.";
  private static final String MSG_LENGTH_TOO_SHORT =
          "The user password must have at least %d characters.";

  private InvalidUserPasswordException(final String message) {
    super(message);
  }

  public static InvalidUserPasswordException becauseValueIsEmpty() {
    return new InvalidUserPasswordException(MSG_VALUE_IS_EMPTY);
  }

  public static InvalidUserPasswordException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserPasswordException(String.format(MSG_LENGTH_TOO_SHORT, minimumLength));
  }
}