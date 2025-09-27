package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.infrastructure.entity.CompanyEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    public CompanyEntity toEntity(Company company, MapperFacade mappers) {
        return CompanyEntity.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .address(company.getAddress())
                .phone(company.getPhone())
                .services(company.getServices().stream()
                        .map(service -> mappers.serviceMapper.toEntity(service, mappers))
                        .collect(Collectors.toList()))
                .employees(company.getEmployees().stream()
                        .map(employee -> mappers.employeeMapper.toEntity(employee, mappers))
                        .collect(Collectors.toList()))
                .build();
    }

    public Company toDomain(CompanyEntity entity, MapperFacade mappers) {
        return Company.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .services(Optional.ofNullable(entity.getServices()).orElse(List.of()).stream()
                        .map(service -> mappers.serviceMapper.toDomain(service, mappers))
                        .collect(Collectors.toList()))
                .employees(Optional.ofNullable(entity.getEmployees()).orElse(List.of()).stream()
                        .map(employee -> mappers.employeeMapper.toDomain(employee, mappers))
                        .collect(Collectors.toList()))
                .build();
    }
}
