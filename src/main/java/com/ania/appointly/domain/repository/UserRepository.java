package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user); // create or update
    boolean existsById(UUID id);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findByCompanyId(UUID companyId);
    List<User> findByRole(Role role);
    void deleteById(UUID id);
}
