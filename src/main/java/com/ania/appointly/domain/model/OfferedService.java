package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.ServiceValidationException;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
public class OfferedService {
    private final UUID id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Duration duration;
    private final Company company;
    private final List<Employee> availableEmployees;

    @Builder
    private OfferedService(UUID id, String name, String description, BigDecimal price, Duration duration, Company company,
                           @Singular("availableEmployee") List<Employee> availableEmployees) {
        if(id == null) throw new ServiceValidationException("Service id cannot be null.");
        this.id = id;
        if(isBlank(name)) throw new ServiceValidationException("Service name cannot be null or empty.");
        this.name = name;
        if(isBlank(description)) throw new ServiceValidationException("Service description cannot be null or empty.");
        this.description = description;
        if(price == null) throw new ServiceValidationException("Service price cannot be null.");
        this.price = price;
        if(duration == null) throw new ServiceValidationException("Service duration cannot be null.");
        if (duration.isNegative()) throw new ServiceValidationException("Service duration cannot be negative.");
        this.duration = duration;
        if (company == null) throw new ServiceValidationException("Service must have a company");
        this.company = company;
        this.availableEmployees = List.copyOf(availableEmployees != null ? availableEmployees : new ArrayList<>());
    }
}
