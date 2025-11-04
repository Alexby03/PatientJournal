package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import core.enums.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "patients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class Patient extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID patientId;

    @Column(nullable = false)
    public String fullName;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserType userType;

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    public List<Condition> conditions = new ArrayList<>();

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    public List<Encounter> encounters = new ArrayList<>();

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    public List<Observation> observations = new ArrayList<>();

    // Default constructor for JPA
    public Patient() {
    }

    // Constructor for creating patients
    public Patient(String fullName, String email, String password, UserType userType) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Convenience methods for managing bidirectional relationships
    public void addCondition(Condition condition) {
        conditions.add(condition);
        condition.patient = this;
    }

    public void removeCondition(Condition condition) {
        conditions.remove(condition);
        condition.patient = null;
    }

    public void addEncounter(Encounter encounter) {
        encounters.add(encounter);
        encounter.patient = this;
    }

    public void removeEncounter(Encounter encounter) {
        encounters.remove(encounter);
        encounter.patient = null;
    }

    public void addObservation(Observation observation) {
        observations.add(observation);
        observation.patient = this;
    }

    public void removeObservation(Observation observation) {
        observations.remove(observation);
        observation.patient = null;
    }
}
