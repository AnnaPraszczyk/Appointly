package com.ania.appointly.infrastructure.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employees")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmployeeEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @ElementCollection
    @CollectionTable(name = "employee_specializations", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "specialization")
    private List<String> specializations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "employee_schedule", joinColumns = @JoinColumn(name = "employee_id"))
    private List<ScheduleSlotEmbeddable> schedule = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations = new ArrayList<>();
}
