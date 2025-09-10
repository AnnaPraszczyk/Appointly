package com.ania.appointly.application.usecase.company;
import com.ania.appointly.domain.model.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadCompanyUseCase {
    boolean existsCompanyById(UUID id);
    Optional<Company> getCompanyById(UUID id);
    List<Company> getAllCompanies();
}
