package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.Employee;
import com.ania.appointly.infrastructure.entity.EmployeeEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeEntity toEntity(Employee employee, MapperFacade mappers) {
        return EmployeeEntity.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .company(mappers.companyMapper.toEntity(employee.getCompany(), mappers))
                .specializations(new ArrayList<>(employee.getSpecializations()))
                .schedule(employee.getSchedule().stream()
                        .map(mappers.scheduleSlotMapper::toEmbeddable)
                        .collect(Collectors.toList()))
                .reservations(employee.getReservations().stream()
                        .map(reservation -> mappers.reservationMapper.toEntity(
                                reservation,
                                mappers))
                        .collect(Collectors.toList()))
                .build();
    }

    public Employee toDomain(EmployeeEntity entity, MapperFacade mappers) {
        return Employee.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .company(mappers.companyMapper.toDomain(entity.getCompany(), mappers))
                .specializations(Optional.ofNullable(entity.getSpecializations()).orElse(List.of()))
                .schedule(Optional.ofNullable(entity.getSchedule()).orElse(List.of()).stream()
                        .map(mappers.scheduleSlotMapper::toDomain)
                        .collect(Collectors.toList()))
                .reservations(Optional.ofNullable(entity.getReservations()).orElse(List.of()).stream()
                        .map(reservation -> mappers.reservationMapper.toDomain(reservation, mappers))
                        .collect(Collectors.toList()))
                .build();
    }
}
