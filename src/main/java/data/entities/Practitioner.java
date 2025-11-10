package data.entities;

import core.enums.UserType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PRACTITIONER")
public class Practitioner extends User {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Condition> conditions = new ArrayList<>();

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Encounter> encounters = new ArrayList<>();

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Observation> observations = new ArrayList<>();

    public Practitioner() {}

    public Practitioner(String fullName, String email, String password, UserType userType, Organization organization) {
        super(fullName, email, password, userType);
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
        condition.setPractitioner(this);
    }

    public void removeCondition(Condition condition) {
        conditions.remove(condition);
        condition.setPractitioner(null);
    }

    public void addEncounter(Encounter encounter) {
        encounters.add(encounter);
        encounter.setPractitioner(this);
    }

    public void removeEncounter(Encounter encounter) {
        encounters.remove(encounter);
        encounter.setPractitioner(null);
    }

    public void addObservation(Observation observation) {
        observations.add(observation);
        observation.setPractitioner(this);
    }

    public void removeObservation(Observation observation) {
        observations.remove(observation);
        observation.setPractitioner(null);
    }
}
