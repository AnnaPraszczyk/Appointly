package com.ania.appointly.domain.model;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ScheduleSlot {
    @EqualsAndHashCode.Include
    private final DayOfWeek day;
    @EqualsAndHashCode.Include
    private final LocalTime time;
    private final boolean available;

    @Builder
    private ScheduleSlot(DayOfWeek day, LocalTime time, boolean available) {
        this.day = Objects.requireNonNull(day, "Schedule slot day cannot be null.");
        this.time = Objects.requireNonNull(time, "Schedule slot time cannot be null.");
        this.available = available;
    }
}
