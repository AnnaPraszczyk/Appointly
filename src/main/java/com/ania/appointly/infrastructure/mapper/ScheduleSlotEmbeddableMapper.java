package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.ScheduleSlot;
import com.ania.appointly.infrastructure.entity.ScheduleSlotEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class ScheduleSlotEmbeddableMapper {
    public ScheduleSlotEmbeddable toEmbeddable(ScheduleSlot slot) {
        return ScheduleSlotEmbeddable.builder()
                .day(slot.getDay())
                .time(slot.getTime())
                .available(slot.isAvailable())
                .build();
    }

    public ScheduleSlot toDomain(ScheduleSlotEmbeddable embeddable) {
        return ScheduleSlot.builder()
                .day(embeddable.getDay())
                .time(embeddable.getTime())
                .available(embeddable.isAvailable())
                .build();
    }
}
