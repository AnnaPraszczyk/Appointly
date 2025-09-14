package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.EmployeeValidationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import java.util.List;
import java.util.UUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
    @EqualsAndHashCode.Include
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final Company company;
    private final List<String> specializations;
    private final List<ScheduleSlot> schedule;
    private final List<Reservation> reservations;

    @Builder
    private Employee(UUID id, String firstName, String lastName, String email, String phoneNumber, Company company,
                     @Singular("specialization") List<String> specializations,
                     @Singular("scheduleSlot") List<ScheduleSlot> schedule,
                     @Singular("reservation") List<Reservation> reservations ) {
        if (id == null) throw new EmployeeValidationException("Employee id cannot be null.");
        this.id = id;
        if(isBlank(firstName)) throw new EmployeeValidationException("Employee first name cannot be null or empty.");
        this.firstName = firstName;
        if(isBlank(lastName)) throw new EmployeeValidationException("Employee last name cannot be null or empty.");
        this.lastName = lastName;
        if(isBlank(email)) throw new EmployeeValidationException("Employee email cannot be null or empty.");
        this.email = email;
        if(isBlank(phoneNumber)) throw new EmployeeValidationException("Employee phone number cannot be null or empty.");
        this.phoneNumber = phoneNumber;
        if (company == null) throw new EmployeeValidationException("Employee must have a company");
        this.company = company;
        this.specializations = List.copyOf(specializations != null ? specializations : List.of());
        this.schedule = List.copyOf(schedule != null ? schedule : List.of());
        this.reservations = List.copyOf(reservations != null ? reservations : List.of());
    }
}
