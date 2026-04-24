package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Regla 4: @UtilityClass genera constructor privado y evita instanciación accidental
@UtilityClass
public class DatabaseConnectionFactory {

  // Regla 4: método declarado static — no usa estado de instancia
  public static Connection createConnection(final DatabaseConfig config) {
    try {
      return DriverManager.getConnection(
              config.buildJdbcUrl(), config.username(), config.password());
    } catch (final SQLException exception) {
      throw PersistenceException.becauseConnectionFailed(exception);
    }
  }
}