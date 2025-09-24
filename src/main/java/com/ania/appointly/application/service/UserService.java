package com.ania.appointly.application.service;
import com.ania.appointly.application.usecase.user.CreateUserUseCase;
import com.ania.appointly.application.usecase.user.DeleteUserUseCase;
import com.ania.appointly.application.usecase.user.ReadUserUseCase;
import com.ania.appointly.application.usecase.user.UpdateUserUseCase;
import com.ania.appointly.domain.exeptions.UserValidationException;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import com.ania.appointly.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements
        CreateUserUseCase,  ReadUserUseCase,
        UpdateUserUseCase, DeleteUserUseCase {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if(userRepository.existsById(user.getId())){
            throw new UserValidationException("User with this ID already exists.");
        }
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
        if(!userRepository.existsById(user.getId())){
            throw new UserValidationException("User with this ID does not exist.");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsersPaged(Pageable pageable) {
        return userRepository.findAllPaged(pageable);
    }

    @Override
    public void deleteUser(UUID id) {
        if(!userRepository.existsById(id)){
            throw new UserValidationException("User with this ID does not exist.");
        }
        userRepository.deleteById(id);
    }
}
