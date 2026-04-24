package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import lombok.Value;
import lombok.AllArgsConstructor;

// Regla 15: @Value hace todos los campos final y elimina setters públicos — modelo inmutable
// Regla 9: eliminado import de UserEntity (infraestructura) — el dominio no depende hacia afuera
@Value
@AllArgsConstructor
public class UserModel {

  UserId id;
  UserName name;
  UserEmail email;
  UserPassword password;
  UserRole role;
  UserStatus status;

  public static UserModel create(
          final UserId id,
          final UserName name,
          final UserEmail email,
          final UserPassword password,
          final UserRole role) {
    return new UserModel(id, name, email, password, role, UserStatus.PENDING);
  }

  // Regla 13: isAllowedToLogin pertenece al modelo de dominio
  public boolean isAllowedToLogin() {
    return this.status == UserStatus.ACTIVE;
  }

  // Regla 13: isAdmin pertenece al modelo de dominio
  public boolean isAdmin() {
    return this.role == UserRole.ADMIN;
  }

  // Regla 14: passwordMatches delega al value object sin exponer internals
  public boolean passwordMatches(final String plainPassword) {
    return this.password.verifyPlain(plainPassword);
  }

  public UserModel activate() {
    return new UserModel(id, name, email, password, role, UserStatus.ACTIVE);
  }

  public UserModel deactivate() {
    return new UserModel(id, name, email, password, role, UserStatus.INACTIVE);
  }

  // Regla 9: eliminado toEntity() — la conversión a entidad de infraestructura
  // pertenece al mapper o adapter de persistencia, no al dominio
}