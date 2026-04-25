package com.jcaa.usersmanagement.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class AppProperties {

  private static final String PROPERTIES_FILE = "application.properties";

  private final Properties properties;

  public AppProperties() {
    this(AppProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
  }

  // Package-private — test entry point
  AppProperties(final InputStream stream) {
    this.properties = doLoad(stream);
  }

  private static Properties doLoad(final InputStream stream) {
    // Regla 4: reemplazado == null por Objects.requireNonNull()
    Objects.requireNonNull(stream, "File not found in classpath: " + PROPERTIES_FILE);
    // Regla 4: renombrado "props" a "properties" — nombre descriptivo sin abreviatura
    final Properties properties = new Properties();
    try (stream) {
      properties.load(stream);
    } catch (final IOException exception) {
      throw ConfigurationException.becauseLoadFailed(exception);
    }
    return properties;
  }

  public String get(final String key) {
    // Regla 4: renombrado "val" a "value" — nombre descriptivo sin abreviatura
    final String value = properties.getProperty(key);
    // Regla 4: reemplazado == null por Objects.requireNonNull()
    Objects.requireNonNull(value, "Property not found in " + PROPERTIES_FILE + ": " + key);
    return value;
  }

  public int getInt(final String key) {
    return Integer.parseInt(get(key));
  }
}