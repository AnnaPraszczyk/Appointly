package com.ania.appointly.application.service;
import com.ania.appointly.application.usecase.service.CreateServiceUseCase;
import com.ania.appointly.application.usecase.service.DeleteServiceUseCase;
import com.ania.appointly.application.usecase.service.ReadServiceUseCase;
import com.ania.appointly.application.usecase.service.UpdateServiceUseCase;
import com.ania.appointly.domain.exeptions.ServiceValidationException;
import com.ania.appointly.domain.model.OfferedService;
import com.ania.appointly.domain.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferedServiceService implements CreateServiceUseCase, ReadServiceUseCase, UpdateServiceUseCase, DeleteServiceUseCase {
    private final ServiceRepository serviceRepository;

    @Override
    public OfferedService createService(OfferedService service) {
        if (serviceRepository.existsById(service.getId())) {
            throw new ServiceValidationException("Service with this ID already exists.");
        }
        return serviceRepository.save(service);
    }

    @Override
    public Optional<OfferedService> getServiceById(UUID id) {
        return serviceRepository.findById(id);
    }

    @Override
    public List<OfferedService> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public List<OfferedService> getServicesByCompany(UUID companyId) {
        return serviceRepository.findByCompanyId(companyId);
    }

    @Override
    public List<OfferedService> getServicesByEmployee(UUID employeeId) {
        return serviceRepository.findByEmployeeId(employeeId);
    }

    @Override
    public OfferedService updateService(OfferedService service) {
        if (!serviceRepository.existsById(service.getId())) {
            throw new ServiceValidationException("Cannot update non-existing service.");
        }
        return serviceRepository.save(service);
    }

    @Override
    public void deleteService(UUID id) {
        if (!serviceRepository.existsById(id)) {
            throw new ServiceValidationException("Cannot delete non-existing service.");
        }
        serviceRepository.deleteById(id);
    }
}
