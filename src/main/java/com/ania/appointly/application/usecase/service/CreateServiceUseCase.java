package com.ania.appointly.application.usecase.service;
import com.ania.appointly.domain.model.Service;

public interface CreateServiceUseCase {
    Service createService(Service service);
}
