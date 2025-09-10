package com.ania.appointly.application.usecase.employee;
import com.ania.appointly.domain.model.ScheduleSlot;
import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface GetAvailableSlotsUseCase {
    List<ScheduleSlot> getAvailableSlots(UUID employeeId, DayOfWeek day);
}
