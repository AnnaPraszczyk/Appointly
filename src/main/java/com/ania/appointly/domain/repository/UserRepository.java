package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    boolean existsById(UUID id);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findByCompanyId(UUID companyId);
    List<User> findByRole(Role role);
    List<User> findAllPaged(Pageable pageable);
    void deleteById(UUID id);
}
