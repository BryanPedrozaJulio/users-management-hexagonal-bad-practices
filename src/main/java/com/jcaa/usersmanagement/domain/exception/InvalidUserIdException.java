package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserIdException extends DomainException {

  // ✅ Regla 10: texto extraído a constante con nombre descriptivo
  private static final String MSG_VALUE_IS_EMPTY =
          "The user id must not be empty.";

  private InvalidUserIdException(final String message) {
    super(message);
  }

  public static InvalidUserIdException becauseValueIsEmpty() {
    return new InvalidUserIdException(MSG_VALUE_IS_EMPTY);
  }
}