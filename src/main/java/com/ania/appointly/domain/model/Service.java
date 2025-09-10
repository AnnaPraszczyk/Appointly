package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.ServiceValidationException;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
public class Service {
    private final UUID id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Duration duration;
    private final Company company;
    private final List<Employee> availableEmployees;

    @Builder
    private Service(UUID id, String name, String description, BigDecimal price, Duration duration, Company company,
                    @Singular("availableEmployee") List<Employee> availableEmployees) {
        this.id = Objects.requireNonNull(id, "Service id cannot be null.");
        if(isBlank(name)) throw new ServiceValidationException("Service name cannot be null or empty.");
        this.name = name;
        if(isBlank(description)) throw new ServiceValidationException("Service description cannot be null or empty.");
        this.description = description;
        this.price = Objects.requireNonNull(price, "Service price cannot be null.");
        this.duration = Objects.requireNonNull(duration, "Service duration cannot be null.");
        this.company = Objects.requireNonNull(company, "Service company cannot be null.");
        this.availableEmployees = List.copyOf(availableEmployees != null ? availableEmployees : new ArrayList<>());
    }
}
