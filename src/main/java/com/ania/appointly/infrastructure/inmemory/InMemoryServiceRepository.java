package com.ania.appointly.infrastructure.inmemory;
import com.ania.appointly.domain.model.OfferedService;
import com.ania.appointly.domain.repository.ServiceRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryServiceRepository implements ServiceRepository {
    private final Map<UUID, OfferedService> storage = new HashMap<>();

    @Override
    public OfferedService save(OfferedService service) {
        storage.put(service.getId(), service);
        return service;
    }

    @Override
    public boolean existsById(UUID id) {
        return storage.containsKey(id);
    }

    @Override
    public Optional<OfferedService> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<OfferedService> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<OfferedService> findByCompanyId(UUID companyId) {
        return storage.values().stream()
                .filter(s -> s.getCompany().getId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferedService> findByEmployeeId(UUID employeeId) {
        return storage.values().stream()
                .filter(s -> s.getAvailableEmployees().stream()
                        .anyMatch(e -> e.getId().equals(employeeId)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
