package com.ania.appointly.domain.model;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleSlotTest {
    @Test
    void createScheduleSlotWithValidData() {
        ScheduleSlot slot = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();

        assertEquals(DayOfWeek.MONDAY, slot.getDay());
        assertEquals(LocalTime.of(10, 0), slot.getTime());
        assertTrue(slot.isAvailable());
    }
    @Test
    void throwExceptionWhenDayIsNull() {
        assertThrows(NullPointerException.class, () ->
                ScheduleSlot.builder()
                        .day(null)
                        .time(LocalTime.of(10, 0))
                        .available(true)
                        .build()
        );
    }

    @Test
    void throwExceptionWhenTimeIsNull() {
        assertThrows(NullPointerException.class, () ->
                ScheduleSlot.builder()
                        .day(DayOfWeek.MONDAY)
                        .time(null)
                        .available(true)
                        .build()
        );
    }

    @Test
    void compareSlotsByDayAndTime() {
        ScheduleSlot slot1 = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();

        ScheduleSlot slot2 = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(10, 0))
                .available(false)
                .build();

        assertEquals(slot1, slot2);
        assertEquals(slot1.hashCode(), slot2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfDayOrTimeDiffers() {
        ScheduleSlot slot1 = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();

        ScheduleSlot slot2 = ScheduleSlot.builder()
                .day(DayOfWeek.TUESDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();

        ScheduleSlot slot3 = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(11, 0))
                .available(true)
                .build();

        assertNotEquals(slot1, slot2);
        assertNotEquals(slot1, slot3);
    }
}