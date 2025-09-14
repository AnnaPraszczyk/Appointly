package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.CompanyValidationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import java.util.List;
import java.util.UUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Company {
    @EqualsAndHashCode.Include
    private final UUID id;
    private final String name;
    private final String description;
    private final String address;
    private final String phone;
    private final List<OfferedService> services;
    private final List<Employee> employees;

    @Builder
    private Company(UUID id, String name, String description, String address, String phone,
                   @Singular("service") List<OfferedService> services,
                   @Singular("employee") List<Employee> employees) {
        if (id == null) throw new CompanyValidationException("Company id cannot be null.");
        this.id = id;
        if(isBlank(name)) throw new CompanyValidationException("Company name cannot be null or empty.");
        this.name = name;
        if(isBlank(description)) throw new CompanyValidationException("Company description cannot be null or empty.");
        this.description = description;
        if(isBlank(address)) throw new CompanyValidationException("Company address cannot be null or empty.");
        this.address = address;
        if(isBlank(phone)) throw new CompanyValidationException("Company phone cannot be null or empty.");
        this.phone = phone;
        this.services = List.copyOf(services != null ? services : List.of());
        this.employees = List.copyOf(employees != null ? employees : List.of());
    }
}
