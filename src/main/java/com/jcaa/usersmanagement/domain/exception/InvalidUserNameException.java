package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserNameException extends DomainException {

  // Regla 10: textos extraídos a constantes con nombre descriptivo
  private static final String MSG_VALUE_IS_EMPTY =
          "The user name must not be empty.";
  private static final String MSG_LENGTH_TOO_SHORT =
          "The user name must have at least %d characters.";

  private InvalidUserNameException(final String message) {
    super(message);
  }

  public static InvalidUserNameException becauseValueIsEmpty() {
    return new InvalidUserNameException(MSG_VALUE_IS_EMPTY);
  }

  public static InvalidUserNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserNameException(String.format(MSG_LENGTH_TOO_SHORT, minimumLength));
  }
}