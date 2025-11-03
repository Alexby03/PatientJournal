package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import core.enums.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "practitioners",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class PractitionerDb extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID doctorId;

    @Column(nullable = false)
    public String fullName;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserType userType;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<ConditionDb> conditions = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<EncounterDb> encounters = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<ObservationDb> observations = new ArrayList<>();

    // Default constructor for JPA
    public PractitionerDb() {}

    // Constructor for creation
    public PractitionerDb(String fullName, String email, String password, UserType userType) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public void addCondition(ConditionDb condition) {
        conditions.add(condition);
        condition.doctor = this;
    }

    public void removeCondition(ConditionDb condition) {
        conditions.remove(condition);
        condition.doctor = null;
    }

    public void addEncounter(EncounterDb encounter) {
        encounters.add(encounter);
        encounter.doctor = this;
    }

    public void removeEncounter(EncounterDb encounter) {
        encounters.remove(encounter);
        encounter.doctor = null;
    }

    public void addObservation(ObservationDb observation) {
        observations.add(observation);
        observation.doctor = this;
    }

    public void removeObservation(ObservationDb observation) {
        observations.remove(observation);
        observation.doctor = null;
    }
}
