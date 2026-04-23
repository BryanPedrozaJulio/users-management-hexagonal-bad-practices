package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.port.out.SaveUserPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateUserCommand;
import com.jcaa.usersmanagement.application.service.mapper.UserApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Set;

@Log
@RequiredArgsConstructor
public final class CreateUserService implements CreateUserUseCase {

  private final SaveUserPort saveUserPort;
  private final GetUserByEmailPort getUserByEmailPort;
  private final EmailNotificationService emailNotificationService;
  private final Validator validator;

  @Override
  public UserModel execute(final CreateUserCommand command) {
    validateCommand(command);
    checkEmailNotTaken(command.email());
    final UserModel userToSave = UserApplicationMapper.fromCreateCommandToModel(command);
    final UserModel savedUser = saveUserPort.save(userToSave);
    emailNotificationService.notifyUserCreated(savedUser, command.password());
    return savedUser;
  }

  // Regla 1, 2, 3: validación extraída a su propio método — una sola responsabilidad
  private void validateCommand(final CreateUserCommand command) {
    final Set<ConstraintViolation<CreateUserCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  // Regla 1, 2, 3: verificación de negocio extraída — nombre expresivo, sin comentario
  // Regla 9: el nombre del método reemplaza al comentario anterior
  // Regla 10: eliminados comentarios redundantes
  private void checkEmailNotTaken(final String emailValue) {
    final UserEmail email = new UserEmail(emailValue);
    if (getUserByEmailPort.getByEmail(email).isPresent()) {
      throw UserAlreadyExistsException.becauseEmailAlreadyExists(email.value());
    }
  }
}