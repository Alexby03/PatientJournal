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
public class PatientDb extends PanacheEntityBase {

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
    public List<ConditionDb> conditions = new ArrayList<>();

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    public List<EncounterDb> encounters = new ArrayList<>();

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    public List<ObservationDb> observations = new ArrayList<>();

    // Default constructor for JPA
    public PatientDb() {
    }

    // Constructor for creating patients
    public PatientDb(String fullName, String email, String password, UserType userType) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Convenience methods for managing bidirectional relationships
    public void addCondition(ConditionDb condition) {
        conditions.add(condition);
        condition.patient = this;
    }

    public void removeCondition(ConditionDb condition) {
        conditions.remove(condition);
        condition.patient = null;
    }

    public void addEncounter(EncounterDb encounter) {
        encounters.add(encounter);
        encounter.patient = this;
    }

    public void removeEncounter(EncounterDb encounter) {
        encounters.remove(encounter);
        encounter.patient = null;
    }

    public void addObservation(ObservationDb observation) {
        observations.add(observation);
        observation.patient = this;
    }

    public void removeObservation(ObservationDb observation) {
        observations.remove(observation);
        observation.patient = null;
    }
}
