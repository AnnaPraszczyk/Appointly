package com.ania.appointly.application.usecase.service;
import com.ania.appointly.domain.model.OfferedService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadServiceUseCase {
    Optional<OfferedService> getServiceById(UUID id);
    List<OfferedService> getAllServices();
    List<OfferedService> getServicesByCompany(UUID companyId);
    List<OfferedService> getServicesByEmployee(UUID employeeId);
}
