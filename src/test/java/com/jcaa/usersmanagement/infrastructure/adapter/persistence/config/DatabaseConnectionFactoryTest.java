package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Regla 11: javadoc documentando los casos cubiertos por esta clase de test.
 *
 * Casos cubiertos:
 * - createConnection() retorna la conexión provista por DriverManager en flujo feliz
 * - createConnection() lanza PersistenceException cuando DriverManager falla con SQLException
 */
@DisplayName("DatabaseConnectionFactory")
@ExtendWith(MockitoExtension.class)
class DatabaseConnectionFactoryTest {

  private static final String HOST     = "localhost";
  private static final int    PORT     = 3306;
  private static final String DB_NAME  = "test_db";
  private static final String USERNAME = "test_user";
  private static final String PASSWORD = "test_pass";

  @Mock private Connection mockConnection;

  private DatabaseConfig config;

  @BeforeEach
  void setUp() {
    config = new DatabaseConfig(HOST, PORT, DB_NAME, USERNAME, PASSWORD);
    // Regla 4: eliminada instancia de factory — se llama directamente como @UtilityClass
  }

  // ── createConnection() — happy path

  @Test
  @DisplayName("createConnection() returns the connection provided by DriverManager")
  void shouldReturnConnectionWhenDriverManagerSucceeds() {
    // Arrange
    try (final MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager
              .when(() -> DriverManager.getConnection(any(), any(), any()))
              .thenReturn(mockConnection);

      // Act
      // Regla 4: llamada estática directa a DatabaseConnectionFactory
      final Connection result = DatabaseConnectionFactory.createConnection(config);

      // Assert
      assertSame(mockConnection, result, "must return the connection provided by DriverManager");
    }
  }

  // ── createConnection() — SQLException → PersistenceException

  @Test
  @DisplayName("createConnection() throws PersistenceException when DriverManager fails")
  void shouldThrowPersistenceExceptionWhenDriverManagerFails() {
    // Arrange
    final SQLException cause = new SQLException("Connection refused");
    try (final MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
      mockedDriverManager
              .when(() -> DriverManager.getConnection(any(), any(), any()))
              .thenThrow(cause);

      // Act & Assert
      // Regla 4: llamada estática directa a DatabaseConnectionFactory
      assertThrows(
              PersistenceException.class,
              () -> DatabaseConnectionFactory.createConnection(config),
              "must throw PersistenceException when DriverManager throws SQLException");
    }
  }
}