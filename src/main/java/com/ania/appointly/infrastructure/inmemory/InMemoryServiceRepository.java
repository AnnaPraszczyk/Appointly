package com.ania.appointly.infrastructure.inmemory;
import com.ania.appointly.domain.model.OfferedService;
import com.ania.appointly.domain.repository.ServiceRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("test")
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
    public Optional<OfferedService> findByName(String name) {
        return storage.values().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<OfferedService> findAllPaged(Pageable pageable) {
        List<OfferedService> all = findAll();
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
