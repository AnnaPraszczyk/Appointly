package com.ania.appointly.application.usecase.user;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadUserUseCase {
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByCompany(UUID companyId);
    List<User> getUsersByRole(Role role);
}
