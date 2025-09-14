package com.ania.appointly.infrastructure.inmemory;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.repository.CompanyRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class InMemoryCompanyRepository implements CompanyRepository {
    private final Map<UUID, Company> storage = new HashMap<>();

    @Override
    public Company create(Company company) {
        storage.put(company.getId(), company);
        return company;
    }

    @Override
    public boolean existsById(UUID id) {
        return storage.containsKey(id);
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Company> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Company update(Company company) {
        storage.put(company.getId(), company);
        return company;
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
