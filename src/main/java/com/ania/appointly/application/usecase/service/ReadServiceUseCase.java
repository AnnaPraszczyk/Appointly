package com.ania.appointly.application.usecase.service;
import com.ania.appointly.domain.model.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadServiceUseCase {
    Optional<Service> getServiceById(UUID id);
    List<Service> getAllServices();
    List<Service> getServicesByCompany(UUID companyId);
    List<Service> getServicesByEmployee(UUID employeeId);
}
