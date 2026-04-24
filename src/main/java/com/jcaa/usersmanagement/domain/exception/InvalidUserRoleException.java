package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserRoleException extends DomainException {

  // Regla 10: texto extraído a constante con nombre descriptivo
  private static final String MSG_VALUE_IS_INVALID =
          "The user role '%s' is not valid.";

  private InvalidUserRoleException(final String message) {
    super(message);
  }

  public static InvalidUserRoleException becauseValueIsInvalid(final String role) {
    return new InvalidUserRoleException(String.format(MSG_VALUE_IS_INVALID, role));
  }
}