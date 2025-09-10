package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Company create(Company company);
    boolean existsById(UUID id);
    Optional<Company> findById(UUID id);
    List<Company> findAll();
    Company update(Company company);
    void deleteById(UUID id);
}
