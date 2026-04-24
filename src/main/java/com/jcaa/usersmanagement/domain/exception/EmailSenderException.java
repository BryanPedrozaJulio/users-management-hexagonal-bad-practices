package com.jcaa.usersmanagement.domain.exception;

public final class EmailSenderException extends DomainException {

  // ✅ Regla 10: textos extraídos a constantes con nombre descriptivo
  private static final String MSG_SMTP_FAILED =
          "No se pudo enviar el correo a '%s'. Error SMTP: %s";
  private static final String MSG_SEND_FAILED =
          "La notificación por correo no pudo ser enviada.";

  // ✅ Regla 9: constructores privados — solo los factory methods controlan la instanciación
  private EmailSenderException(final String message) {
    super(message);
  }

  private EmailSenderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static EmailSenderException becauseSmtpFailed(
          final String destinationEmail, final String smtpError) {
    return new EmailSenderException(
            String.format(MSG_SMTP_FAILED, destinationEmail, smtpError));
  }

  public static EmailSenderException becauseSendFailed(final Throwable cause) {
    return new EmailSenderException(MSG_SEND_FAILED, cause);
  }
}