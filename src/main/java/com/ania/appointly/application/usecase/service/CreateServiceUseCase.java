package com.ania.appointly.application.usecase.service;
import com.ania.appointly.domain.model.OfferedService;

public interface CreateServiceUseCase {
    OfferedService createService(OfferedService service);
}
