package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.ScheduleSlot;
import com.ania.appointly.infrastructure.entity.ScheduleSlotEmbeddable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleSlotEmbeddableMapperTest {
    private ScheduleSlotEmbeddableMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ScheduleSlotEmbeddableMapper();
    }

    @Test
    void mapDomainToEmbeddable() {
        ScheduleSlot slot = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(9, 0))
                .available(true)
                .build();
        ScheduleSlotEmbeddable result = mapper.toEmbeddable(slot);

        assertEquals(DayOfWeek.MONDAY, result.getDay());
        assertEquals(LocalTime.of(9, 0), result.getTime());
        assertTrue(result.isAvailable());
    }

    @Test
    void mapEmbeddableToDomain() {
        ScheduleSlotEmbeddable embeddable = ScheduleSlotEmbeddable.builder()
                .day(DayOfWeek.FRIDAY)
                .time(LocalTime.of(14, 30))
                .available(false)
                .build();
        ScheduleSlot result = mapper.toDomain(embeddable);

        assertEquals(DayOfWeek.FRIDAY, result.getDay());
        assertEquals(LocalTime.of(14, 30), result.getTime());
        assertFalse(result.isAvailable());
    }

    @Test
    void throwExceptionWhenDayIsNullInDomain() {
        assertThrows(NullPointerException.class, () -> ScheduleSlot.builder()
                .day(null)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build());
    }

    @Test
    void throwExceptionWhenTimeIsNullInDomain() {
        assertThrows(NullPointerException.class, () -> ScheduleSlot.builder()
                .day(DayOfWeek.SUNDAY)
                .time(null)
                .available(true)
                .build());
    }
}