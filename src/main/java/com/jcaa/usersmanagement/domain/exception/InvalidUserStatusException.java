package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserStatusException extends DomainException {

  // Regla 10: texto extraído a constante con nombre descriptivo
  private static final String MSG_VALUE_IS_INVALID =
          "The user status '%s' is not valid.";

  private InvalidUserStatusException(final String message) {
    super(message);
  }

  public static InvalidUserStatusException becauseValueIsInvalid(final String status) {
    return new InvalidUserStatusException(String.format(MSG_VALUE_IS_INVALID, status));
  }
}