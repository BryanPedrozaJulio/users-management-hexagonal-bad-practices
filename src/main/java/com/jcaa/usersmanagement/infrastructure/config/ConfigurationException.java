package com.jcaa.usersmanagement.infrastructure.config;

public final class ConfigurationException extends RuntimeException {

  // Regla 10: texto extraído a constante con nombre descriptivo
  private static final String MSG_LOAD_FAILED =
          "Failed to load the application configuration.";

  private ConfigurationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static ConfigurationException becauseLoadFailed(final Throwable cause) {
    return new ConfigurationException(MSG_LOAD_FAILED, cause);
  }
}