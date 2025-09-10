package com.ania.appointly.application.usecase.employee;
import com.ania.appointly.domain.model.ScheduleSlot;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface UpdateEmployeeScheduleUseCase {
    void updateSchedule(UUID employeeId, List<ScheduleSlot> newSchedule);
    void removeScheduleSlot(UUID employeeId, DayOfWeek day, LocalTime time);
}
