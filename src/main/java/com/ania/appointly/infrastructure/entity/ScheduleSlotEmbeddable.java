package com.ania.appointly.infrastructure.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ScheduleSlotEmbeddable {
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DayOfWeek day;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "available", nullable = false)
    private boolean available;
}
