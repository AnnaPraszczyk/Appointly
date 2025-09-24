package com.ania.appointly.infrastructure.persistence.jpa;
import com.ania.appointly.domain.exeptions.CompanyNotFoundException;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.repository.CompanyRepository;
import com.ania.appointly.infrastructure.entity.CompanyEntity;
import com.ania.appointly.infrastructure.mapper.MapperFacade;
import com.ania.appointly.infrastructure.persistence.repository.SpringDataCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaCompanyRepository implements CompanyRepository {
    private final SpringDataCompanyRepository springRepository;
    private final MapperFacade mappers;

    @Override
    public Company create(Company company) {
        if (springRepository.existsByName(company.getName())) {
            throw new CompanyNotFoundException("Company with name " + company.getName() + " already exists.");
        }
        CompanyEntity entity = mappers.companyMapper.toEntity(company, mappers);
        CompanyEntity saved = springRepository.save(entity);
        return mappers.companyMapper.toDomain(saved, mappers);
    }

    @Override
    public boolean existsById(UUID id) {
        return springRepository.existsById(id);
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return springRepository.findById(id)
                .map(entity -> mappers.companyMapper.toDomain(entity, mappers));
    }

    @Override
    public Optional<Company> findByName(String name) {
        return springRepository.findByName(name)
                .map(entity -> mappers.companyMapper.toDomain(entity, mappers));
    }

    @Override
    public boolean existsByName(String name) {
        return springRepository.existsByName(name);
    }

    @Override
    public Optional<Company> findByPhone(String phone) {
        return springRepository.findByPhone(phone)
                .map(entity -> mappers.companyMapper.toDomain(entity, mappers));
    }

    @Override
    public List<Company> findAll() {
        return springRepository.findAll().stream()
                .map(entity -> mappers.companyMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public Company update(Company company) {
        if (!springRepository.existsById(company.getId())) {
            throw new CompanyNotFoundException("Company with id " + company.getId() + " not found.");
        }
        CompanyEntity entity = mappers.companyMapper.toEntity(company, mappers);
        CompanyEntity updated = springRepository.save(entity);
        return mappers.companyMapper.toDomain(updated, mappers);
    }

    @Override
    public void deleteById(UUID id) {
        springRepository.deleteById(id);
    }
}