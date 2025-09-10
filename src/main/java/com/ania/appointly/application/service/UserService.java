package com.ania.appointly.application.service;
import com.ania.appointly.application.usecase.user.CreateUserUseCase;
import com.ania.appointly.application.usecase.user.DeleteUserUseCase;
import com.ania.appointly.application.usecase.user.ReadUserUseCase;
import com.ania.appointly.application.usecase.user.UpdateUserUseCase;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import com.ania.appointly.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements
        CreateUserUseCase,  ReadUserUseCase,
        UpdateUserUseCase, DeleteUserUseCase {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByCompany(UUID companyId) {
        return userRepository.findByCompanyId(companyId);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
