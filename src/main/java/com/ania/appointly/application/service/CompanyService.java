package com.ania.appointly.application.service;
import com.ania.appointly.application.usecase.company.CreateCompanyUseCase;
import com.ania.appointly.application.usecase.company.DeleteCompanyUseCase;
import com.ania.appointly.application.usecase.company.ReadCompanyUseCase;
import com.ania.appointly.application.usecase.company.UpdateCompanyUseCase;
import com.ania.appointly.domain.exeptions.CompanyValidationException;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService implements CreateCompanyUseCase,
        ReadCompanyUseCase, UpdateCompanyUseCase,
        DeleteCompanyUseCase {
    private final CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        if (companyRepository.existsById(company.getId())) {
            throw new CompanyValidationException("Company with this ID already exists.");
        }
        return companyRepository.create(company);
    }

    @Override
    public boolean existsCompanyById(UUID id) {
        return companyRepository.existsById(id);
    }

    @Override
    public Optional<Company> getCompanyById(UUID id) {
        return companyRepository.findById(id);
    }

    @Override
    public Optional<Company> getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public boolean existsCompanyByName(String name) {
        return companyRepository.existsByName(name);
    }

    @Override
    public Optional<Company> getCompanyByPhone(String phone) {
        return companyRepository.findByPhone(phone);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompany(Company company) {
        if (!companyRepository.existsById(company.getId())) {
            throw new CompanyValidationException("Cannot update non-existing company.");
        }
        return companyRepository.update(company);
    }

    @Override
    public void deleteCompany(UUID id) {
        if (!companyRepository.existsById(id)) {
            throw new CompanyValidationException("Cannot delete non-existing company.");
        }
        companyRepository.deleteById(id);
    }
}
