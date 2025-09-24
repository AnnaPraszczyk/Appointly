package com.ania.appointly.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperFacade {
    public final CompanyMapper companyMapper;
    public final OfferedServiceMapper serviceMapper;
    public final EmployeeMapper employeeMapper;
    public final ReservationMapper reservationMapper;
    public final ScheduleSlotEmbeddableMapper scheduleSlotMapper;
    public final UserMapper userMapper;
}
