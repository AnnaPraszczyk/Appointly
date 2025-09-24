package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.OfferedService;
import com.ania.appointly.infrastructure.entity.OfferedServiceEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OfferedServiceMapper {

    public OfferedServiceEntity toEntity(OfferedService service, MapperFacade mappers) {
        return OfferedServiceEntity.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .duration(service.getDuration())
                .company(mappers.companyMapper.toEntity(service.getCompany(), mappers))
                .availableEmployees(service.getAvailableEmployees().stream()
                        .map(e -> mappers.employeeMapper.toEntity(e, mappers))
                        .collect(Collectors.toList()))
                .build();
    }

    public OfferedService toDomain(OfferedServiceEntity entity, MapperFacade mappers) {
        return OfferedService.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .duration(entity.getDuration())
                .company(mappers.companyMapper.toDomain(entity.getCompany(), mappers))
                .availableEmployees(entity.getAvailableEmployees().stream()
                        .map(e -> mappers.employeeMapper.toDomain(e, mappers))
                        .collect(Collectors.toList()))
                .build();
    }
}
