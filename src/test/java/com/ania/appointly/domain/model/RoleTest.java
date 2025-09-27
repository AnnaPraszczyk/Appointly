package com.ania.appointly.domain.model;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    @Test
    void returnCorrectDisplayNames() {
        assertEquals("Client", Role.CLIENT.getDisplayName());
        assertEquals("Provider", Role.PROVIDER.getDisplayName());
        assertEquals("Employee", Role.EMPLOYEE.getDisplayName());
        assertEquals("Admin", Role.ADMIN.getDisplayName());
    }

    @Test
    void containAllRoles() {
        Role[] roles = Role.values();
        assertEquals(4, roles.length);
        assertTrue(List.of(roles).contains(Role.CLIENT));
        assertTrue(List.of(roles).contains(Role.PROVIDER));
        assertTrue(List.of(roles).contains(Role.EMPLOYEE));
        assertTrue(List.of(roles).contains(Role.ADMIN));
    }

    @Test
    void convertFromStringCorrectly() {
        assertEquals(Role.CLIENT, Role.valueOf("CLIENT"));
        assertEquals(Role.PROVIDER, Role.valueOf("PROVIDER"));
        assertEquals(Role.EMPLOYEE, Role.valueOf("EMPLOYEE"));
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
    }

    @Test
    void throwExceptionForInvalidRole() {
        assertThrows(IllegalArgumentException.class, () -> Role.valueOf("INVALID"));
    }
}