package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidUserNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// Regla 11: agregado @DisplayName a la clase
@DisplayName("UserName")
class UserNameTest {

  @ParameterizedTest
  // Regla 11: agregado @DisplayName descriptivo
  @DisplayName("should create UserName with trimmed value when input has surrounding whitespace")
  @ValueSource(strings = {"John Arrieta", "   John Arrieta   ", "John Arrieta \t"})
  void shouldValidateUserNameMinimumLength(final String userName) {
    // Arrange
    final String expectedValue = "John Arrieta";

    // Act
    final UserName userNameVo = new UserName(userName);

    // Assert
    // Regla 11: reemplazado assertTrue(x.equals(y)) por assertEquals(x, y)
    assertEquals(expectedValue, userNameVo.toString());
  }

  // ── flujo con excepciones y ramas de validación

  @Test
  // Regla 11: agregado @DisplayName descriptivo
  @DisplayName("should throw NullPointerException when value is null")
  void shouldValidateUserNameIsNotNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserName(null));
  }

  @ParameterizedTest
  // Regla 11: agregado @DisplayName descriptivo
  @DisplayName("should throw InvalidUserNameException when value is blank or shorter than minimum length")
  @ValueSource(
          strings = {"", "  ", "\t", "\n", "\r", "\f", "\b", "Jo", "Ty  ", "", "   Cy ", "Ed\t"})
  void shouldValidateUserNameIsNotEmptyAndMinimumLength(final String userName) {
    // Act & Assert
    assertThrows(InvalidUserNameException.class, () -> new UserName(userName));
  }
}