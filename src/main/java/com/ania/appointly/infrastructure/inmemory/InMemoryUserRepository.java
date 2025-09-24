package com.ania.appointly.infrastructure.inmemory;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import com.ania.appointly.domain.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("test")
public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> storage = new HashMap<>();

    @Override
    public User save(User user) {
        storage.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean existsById(UUID id) {
        return storage.containsKey(id);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return storage.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<User> findByCompanyId(UUID companyId) {
        return storage.values().stream()
                .filter(u -> u.getCompany() != null && u.getCompany().getId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRole(Role role) {
        return storage.values().stream()
                .filter(u -> u.getRole() == role)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllPaged(Pageable pageable) {
        List<User> all = new ArrayList<>(storage.values());
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), all.size());
        if (start >= all.size()) {
            return Collections.emptyList();
        }
        return all.subList(start, end);
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
