package com.ania.appointly.application.service;
import com.ania.appointly.domain.exeptions.CompanyValidationException;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.infrastructure.inmemory.InMemoryCompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceTest {
    private CompanyService companyService;
    private InMemoryCompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        companyRepository = new InMemoryCompanyRepository();
        companyService = new CompanyService(companyRepository);
    }

    @Test
    void createCompanySuccessfully() {
        Company company = createSampleCompany();
        Company saved = companyService.createCompany(company);

        assertEquals(company.getId(), saved.getId());
        assertTrue(companyRepository.existsById(company.getId()));
    }

    @Test
    void throwExceptionWhenCreatingCompanyWithExistingId() {
        Company company = createSampleCompany();
        companyRepository.create(company);

        assertThrows(CompanyValidationException.class, () -> companyService.createCompany(company));
    }

    @Test
    void createCompanyWithEmptyServiceAndEmployeeLists() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("Minimal Company")
                .description("No services or employees")
                .address("Nowhere 0")
                .phone("000-000-000")
                .build();

        assertNotNull(company);
        assertTrue(company.getServices().isEmpty());
        assertTrue(company.getEmployees().isEmpty());
    }

    @Test
    void createCompanyWithLongFields() {
        String longText = "x".repeat(1000);

        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name(longText)
                .description(longText)
                .address(longText)
                .phone("999999999")
                .build();

        assertEquals(longText, company.getName());
        assertEquals(longText, company.getDescription());
        assertEquals(longText, company.getAddress());
    }

    @Test
    void updateExistingCompany() {
        Company company = createSampleCompany();
        companyRepository.create(company);
        Company updated = Company.builder()
                .id(company.getId())
                .name("Updated Name")
                .description(company.getDescription())
                .address(company.getAddress())
                .phone(company.getPhone())
                .build();
        Company result = companyService.updateCompany(updated);

        assertEquals("Updated Name", result.getName());
    }

    @Test
    void throwExceptionWhenCompanyIdIsNull() {
        assertThrows(CompanyValidationException.class, () -> Company.builder()
                .id(null)
                .name("Valid Name")
                .description("Valid Description")
                .address("Valid Address")
                .phone("123456789")
                .build());
    }

    @Test
    void throwExceptionWhenCompanyNameIsBlank() {
        assertThrows(CompanyValidationException.class, () -> Company.builder()
                .id(UUID.randomUUID())
                .name(" ")
                .description("Valid Description")
                .address("Valid Address")
                .phone("123456789")
                .build());
    }

    @Test
    void throwExceptionWhenCompanyDescriptionIsBlank() {
        assertThrows(CompanyValidationException.class, () -> Company.builder()
                .id(UUID.randomUUID())
                .name("Valid Name")
                .description("")
                .address("Valid Address")
                .phone("123456789")
                .build());
    }

    @Test
    void throwExceptionWhenCompanyAddressIsBlank() {
        assertThrows(CompanyValidationException.class, () -> Company.builder()
                .id(UUID.randomUUID())
                .name("Valid Name")
                .description("Valid Description")
                .address(" ")
                .phone("123456789")
                .build());
    }

    @Test
    void throwExceptionWhenCompanyPhoneIsBlank() {
        assertThrows(CompanyValidationException.class, () -> Company.builder()
                .id(UUID.randomUUID())
                .name("Valid Name")
                .description("Valid Description")
                .address("Valid Address")
                .phone("")
                .build());
    }

    @Test
    void throwExceptionWhenUpdatingNonExistingCompany() {
        Company company = createSampleCompany();

        assertThrows(CompanyValidationException.class, () -> companyService.updateCompany(company));
    }

    @Test
    void deleteCompanySuccessfully() {
        Company company = createSampleCompany();
        companyRepository.create(company);
        companyService.deleteCompany(company.getId());

        assertFalse(companyRepository.existsById(company.getId()));
    }

    @Test
    void throwExceptionWhenDeletingNonExistingCompany() {
        UUID id = UUID.randomUUID();

        assertThrows(CompanyValidationException.class, () -> companyService.deleteCompany(id));
    }

    @Test
    void returnCompanyById() {
        Company company = createSampleCompany();
        companyRepository.create(company);
        Optional<Company> found = companyService.getCompanyById(company.getId());

        assertTrue(found.isPresent());
        assertEquals(company.getId(), found.get().getId());
    }

    @Test
    void returnEmptyOptionalWhenCompanyNotFound() {
        Optional<Company> result = companyService.getCompanyById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void returnAllCompanies() {
        Company company1 = createSampleCompany();
        Company company2 = Company.builder()
                .id(UUID.randomUUID())
                .name("Another Company")
                .description("Another Description")
                .address("Another Address")
                .phone("555-222-333")
                .build();
        companyRepository.create(company1);
        companyRepository.create(company2);
        List<Company> companies = companyService.getAllCompanies();

        assertEquals(2, companies.size());
    }

    @Test
    void returnTrueIfCompanyExists() {
        Company company = createSampleCompany();
        companyRepository.create(company);

        assertTrue(companyService.existsCompanyById(company.getId()));
    }

    @Test
    void returnFalseIfCompanyDoesNotExist() {
        assertFalse(companyService.existsCompanyById(UUID.randomUUID()));
    }

    @Test
    void companiesWithSameIdShouldBeEqual() {
        UUID sharedId = UUID.randomUUID();

        Company company1 = Company.builder()
                .id(sharedId)
                .name("Company A")
                .description("Desc A")
                .address("Address A")
                .phone("111-111-111")
                .build();

        Company company2 = Company.builder()
                .id(sharedId)
                .name("Company B")
                .description("Desc B")
                .address("Address B")
                .phone("222-222-222")
                .build();

        assertEquals(company1, company2);
        assertEquals(company1.hashCode(), company2.hashCode());
    }

    @Test
    void companiesWithDifferentIdsShouldNotBeEqual() {
        Company company1 = Company.builder()
                .id(UUID.randomUUID())
                .name("Company A")
                .description("Desc A")
                .address("Address A")
                .phone("111-111-111")
                .build();

        Company company2 = Company.builder()
                .id(UUID.randomUUID())
                .name("Company A")
                .description("Desc A")
                .address("Address A")
                .phone("111-111-111")
                .build();

        assertNotEquals(company1, company2);
        assertNotEquals(company1.hashCode(), company2.hashCode());
    }

    @Test
    void companyShouldBeEqualToItself() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("Self Company")
                .description("Self Desc")
                .address("Self Address")
                .phone("000-000-000")
                .build();

        assertEquals(company, company);
    }

    @Test
    void companyShouldNotBeEqualToNull() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("Null Test")
                .description("Desc")
                .address("Addr")
                .phone("123")
                .build();

        assertNotEquals(null, company);
    }

    private Company createSampleCompany() {
        return Company.builder()
                .id(UUID.randomUUID())
                .name("Test Company")
                .description("Test Description")
                .address("Test Address")
                .phone("555-000-111")
                .build();
    }
}