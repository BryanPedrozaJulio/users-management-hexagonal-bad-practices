package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidUserIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// Regla 11: agregado @DisplayName a la clase
@DisplayName("UserId")
class UserIdTest {

  @ParameterizedTest
  // Regla 11: agregado @DisplayName descriptivo
  @DisplayName("should create UserId with trimmed value when input has surrounding whitespace")
  @ValueSource(strings = {" user123 ", "  user123  ", "user123\t"})
  void shouldCreateUserIdWithTrimmedValue(final String input) {
    // Arrange
    final String expectedValue = "user123";

    // Act
    final UserId userId = new UserId(input);

    // Assert
    // Regla 11: reemplazado assertTrue(x.equals(y)) por assertEquals(x, y)
    assertEquals(expectedValue, userId.toString());
  }

  @Test
  // Regla 11: agregado @DisplayName descriptivo
  @DisplayName("should throw NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenUserIdIsNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserId(null));
  }

  @ParameterizedTest
  // Regla 11: agregado @DisplayName descriptivo
  @DisplayName("should throw InvalidUserIdException when value is blank or empty")
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  void shouldThrowIllegalArgumentExceptionWhenUserIdIsEmpty(final String input) {
    // Act & Assert
    assertThrows(InvalidUserIdException.class, () -> new UserId(input));
  }
}