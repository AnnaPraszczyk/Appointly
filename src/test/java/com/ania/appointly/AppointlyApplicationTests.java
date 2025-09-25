package com.ania.appointly;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppointlyApplicationTests {

    @Autowired
    private EntityManager entityManager;


    @Test
    void contextLoads() {
        assertNotNull(entityManager);
    }

}
