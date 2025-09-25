package com.ania.appointly.infrastructure.persistence.jpa;

import com.ania.appointly.domain.model.Company;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaCompanyRepositoryTest {
    @Autowired
    private JpaCompanyRepository companyRepository;

    @Test
    void shouldCreateAndFindCompany() {
        UUID id = UUID.randomUUID();
        Company company = Company.builder()
                .id(id)
                .name("Appointly")
                .description("Platforma do rezerwacji")
                .address("ul. Główna 1, Łodygowice")
                .phone("123456789")
                .build();

        Company saved = companyRepository.create(company);
        assertNotNull(saved);
        assertEquals("Appointly", saved.getName());

        Optional<Company> loaded = companyRepository.findById(id);
        assertTrue(loaded.isPresent());
        assertEquals("123456789", loaded.get().getPhone());
    }
}