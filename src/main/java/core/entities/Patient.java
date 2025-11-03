package core.entities;

import core.enums.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Patient {

    private final UUID patientId;
    private String fullName;
    private String email;
    private String password;
    private final UserType userType;

    // Navigation properties
    private List<Condition> conditions = new ArrayList<>();
    private List<Encounter> encounters = new ArrayList<>();
    private List<Observation> observations = new ArrayList<>();

    // Full constructor (used when loading from DB or reconstructing from data)
    public Patient(UUID patientId, String fullName, String email, String password, UserType userType,
                   List<Condition> conditions, List<Encounter> encounters, List<Observation> observations) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.conditions = (conditions != null) ? conditions : new ArrayList<>();
        this.encounters = (encounters != null) ? encounters : new ArrayList<>();
        this.observations = (observations != null) ? observations : new ArrayList<>();
    }

    // Simplified constructor for new patients
    public Patient(String fullName, String email, String password, UserType userType) {
        this.patientId = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Getters & setters
    public UUID getPatientId() {
        return patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Encounter> getEncounters() {
        return encounters;
    }

    public void setEncounters(List<Encounter> encounters) {
        this.encounters = encounters;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    // Convenience methods
    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    public void removeCondition(Condition condition) {
        this.conditions.remove(condition);
    }

    public void addEncounter(Encounter encounter) {
        this.encounters.add(encounter);
    }

    public void removeEncounter(Encounter encounter) {
        this.encounters.remove(encounter);
    }

    public void addObservation(Observation observation) {
        this.observations.add(observation);
    }

    public void removeObservation(Observation observation) {
        this.observations.remove(observation);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", conditions=" + conditions.size() +
                ", encounters=" + encounters.size() +
                ", observations=" + observations.size() +
                '}';
    }
}
