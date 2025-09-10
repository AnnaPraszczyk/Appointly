package com.ania.appointly.domain.model;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReservationStatusTest {
    @Test
    void returnCorrectDisplayNames() {
        assertEquals("Pending", ReservationStatus.PENDING.getDisplayName());
        assertEquals("Confirmed", ReservationStatus.CONFIRMED.getDisplayName());
        assertEquals("Cancelled", ReservationStatus.CANCELLED.getDisplayName());
        assertEquals("Completed", ReservationStatus.COMPLETED.getDisplayName());
    }

    @Test
    void containAllStatuses() {
        ReservationStatus[] statuses = ReservationStatus.values();
        assertEquals(4, statuses.length);
        assertTrue(List.of(statuses).contains(ReservationStatus.PENDING));
        assertTrue(List.of(statuses).contains(ReservationStatus.CONFIRMED));
        assertTrue(List.of(statuses).contains(ReservationStatus.CANCELLED));
        assertTrue(List.of(statuses).contains(ReservationStatus.COMPLETED));
    }

    @Test
    void convertFromStringCorrectly() {
        assertEquals(ReservationStatus.PENDING, ReservationStatus.valueOf("PENDING"));
        assertEquals(ReservationStatus.CONFIRMED, ReservationStatus.valueOf("CONFIRMED"));
        assertEquals(ReservationStatus.CANCELLED, ReservationStatus.valueOf("CANCELLED"));
        assertEquals(ReservationStatus.COMPLETED, ReservationStatus.valueOf("COMPLETED"));
    }

    @Test
    void throwExceptionForInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> ReservationStatus.valueOf("INVALID"));
    }
}