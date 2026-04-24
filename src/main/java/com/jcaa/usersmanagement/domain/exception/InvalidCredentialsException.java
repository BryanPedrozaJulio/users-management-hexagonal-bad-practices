package com.jcaa.usersmanagement.domain.exception;

public final class InvalidCredentialsException extends DomainException {

  // ✅ Regla 10: textos extraídos a constantes con nombre descriptivo
  private static final String MSG_INVALID_CREDENTIALS =
          "Correo o contraseña incorrectos.";
  private static final String MSG_USER_NOT_ACTIVE =
          "Tu cuenta no está activa. Contacta al administrador.";

  private InvalidCredentialsException(final String message) {
    super(message);
  }

  public static InvalidCredentialsException becauseCredentialsAreInvalid() {
    return new InvalidCredentialsException(MSG_INVALID_CREDENTIALS);
  }

  public static InvalidCredentialsException becauseUserIsNotActive() {
    return new InvalidCredentialsException(MSG_USER_NOT_ACTIVE);
  }
}