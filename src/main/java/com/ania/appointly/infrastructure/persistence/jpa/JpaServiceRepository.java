package com.ania.appointly.infrastructure.persistence.jpa;
import com.ania.appointly.domain.model.OfferedService;
import com.ania.appointly.domain.repository.ServiceRepository;
import com.ania.appointly.infrastructure.entity.OfferedServiceEntity;
import com.ania.appointly.infrastructure.mapper.MapperFacade;
import com.ania.appointly.infrastructure.persistence.repository.SpringDataServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaServiceRepository implements ServiceRepository {
    private final SpringDataServiceRepository springRepository;
    private final MapperFacade mappers;

    @Override
    public OfferedService save(OfferedService service) {
        OfferedServiceEntity entity = mappers.serviceMapper.toEntity(service, mappers);
        OfferedServiceEntity saved = springRepository.save(entity);
        return mappers.serviceMapper.toDomain(saved, mappers);
    }

    @Override
    public boolean existsById(UUID id) {
        return springRepository.existsById(id);
    }

    @Override
    public Optional<OfferedService> findById(UUID id) {
        return springRepository.findById(id)
                .map(entity -> mappers.serviceMapper.toDomain(entity, mappers));
    }

    @Override
    public List<OfferedService> findAll() {
        return springRepository.findAll().stream()
                .map(entity -> mappers.serviceMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferedService> findByCompanyId(UUID companyId) {
        return springRepository.findByCompany_Id(companyId).stream()
                .map(entity -> mappers.serviceMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferedService> findByEmployeeId(UUID employeeId) {
        return springRepository.findByEmployeeId(employeeId).stream()
                .map(entity -> mappers.serviceMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<OfferedService> findByName(String name) {
        return springRepository.findByName(name)
                .map(entity -> mappers.serviceMapper.toDomain(entity, mappers));
    }

    @Override
    public List<OfferedService> findAllPaged(Pageable pageable) {
        return springRepository.findAll(pageable).stream()
                .map(entity -> mappers.serviceMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springRepository.deleteById(id);
    }
}
