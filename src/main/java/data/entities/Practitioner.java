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
public class Practitioner extends PanacheEntityBase {

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
    public List<Condition> conditions = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Encounter> encounters = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Observation> observations = new ArrayList<>();

    // Default constructor for JPA
    public Practitioner() {}

    // Constructor for creation
    public Practitioner(String fullName, String email, String password, UserType userType) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
        condition.doctor = this;
    }

    public void removeCondition(Condition condition) {
        conditions.remove(condition);
        condition.doctor = null;
    }

    public void addEncounter(Encounter encounter) {
        encounters.add(encounter);
        encounter.doctor = this;
    }

    public void removeEncounter(Encounter encounter) {
        encounters.remove(encounter);
        encounter.doctor = null;
    }

    public void addObservation(Observation observation) {
        observations.add(observation);
        observation.doctor = this;
    }

    public void removeObservation(Observation observation) {
        observations.remove(observation);
        observation.doctor = null;
    }
}
