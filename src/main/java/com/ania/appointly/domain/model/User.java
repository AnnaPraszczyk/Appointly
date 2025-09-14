package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.UserValidationException;
import lombok.Builder;
import lombok.Getter;
import java.util.Objects;
import java.util.UUID;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
public class User {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final Role role;
    private Company company;

    @Builder
    private User(UUID id, String firstName, String lastName, String email, String phoneNumber, Role role,
                 Company company) {
        if (id == null) throw new UserValidationException("User id cannot be null.");
        this.id = id;
        if(isBlank(firstName)) throw new UserValidationException("User first name cannot be null or empty.");
        this.firstName = firstName;
        if(isBlank(lastName)) throw new UserValidationException("User last name cannot be null or empty.");
        this.lastName = lastName;
        if(isBlank(email)) throw new UserValidationException("User email cannot be null or empty.");
        this.email = email;
        if(isBlank(phoneNumber)) throw new UserValidationException("User phone number cannot be null or empty.");
        this.phoneNumber = phoneNumber;
        this.role = Objects.requireNonNull(role, "User role cannot be null.");
        if (role == Role.PROVIDER && company == null)
            throw new UserValidationException("Provider must have a company");
        if (role != Role.PROVIDER && company != null)
            throw new UserValidationException("Only providers can have a company");
        this.company = company;
    }
}
