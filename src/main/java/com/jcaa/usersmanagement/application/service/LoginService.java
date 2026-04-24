package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.service.dto.command.LoginCommand;
import com.jcaa.usersmanagement.domain.exception.InvalidCredentialsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class LoginService implements LoginUseCase {

  private final GetUserByEmailPort getUserByEmailPort;
  private final Validator validator;

  @Override
  public UserModel execute(final LoginCommand command) {
    validateCommand(command);

    final UserEmail email = new UserEmail(command.email());

    // Regla 8: renombrado para reflejar que solo consulta, sin efectos secundarios
    // Regla 1 y 2: responsabilidades separadas en métodos cortos y expresivos
    final UserModel user = findUserOrFail(email);
    verifyPassword(user, command.password());
    verifyUserIsActive(user);

    return user;
  }

  // Regla 1 y 2: solo busca el usuario y lanza excepción si no existe
  // Regla 8: método de solo consulta, sin efectos secundarios
  private UserModel findUserOrFail(final UserEmail email) {
    return getUserByEmailPort.getByEmail(email)
            .orElseThrow(InvalidCredentialsException::becauseCredentialsAreInvalid);
  }

  // Regla 14: se delega la verificación al modelo en lugar de navegar sus internals
  private void verifyPassword(final UserModel user, final String plainPassword) {
    if (!user.passwordMatches(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
  }

  // Regla 17: condición compleja extraída a método con nombre significativo en UserModel
  // Regla 12: lógica de dominio delegada al modelo donde pertenece
  private void verifyUserIsActive(final UserModel user) {
    if (!user.isAllowedToLogin()) {
      throw InvalidCredentialsException.becauseUserIsNotActive();
    }
  }

  private void validateCommand(final LoginCommand command) {
    final Set<ConstraintViolation<LoginCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}