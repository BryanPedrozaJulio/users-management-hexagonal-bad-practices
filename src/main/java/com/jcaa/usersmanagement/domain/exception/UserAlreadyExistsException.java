package com.jcaa.usersmanagement.domain.exception;

public final class UserAlreadyExistsException extends DomainException {

  // Regla 10: texto extraído a constante con nombre descriptivo
  private static final String MSG_EMAIL_ALREADY_EXISTS =
          "A user with email '%s' already exists.";

  private UserAlreadyExistsException(final String message) {
    super(message);
  }

  public static UserAlreadyExistsException becauseEmailAlreadyExists(final String email) {
    return new UserAlreadyExistsException(String.format(MSG_EMAIL_ALREADY_EXISTS, email));
  }
}