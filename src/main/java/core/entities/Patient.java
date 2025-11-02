package core.entities;

import core.enums.UserType;
import java.util.ArrayList;
import java.util.UUID;

public class Patient {

    private final UUID patientId;
    private String fullName;
    private String email;
    private String password;
    private final UserType userType;
    private final ArrayList<Condition> conditions;
    private final ArrayList<Encounter> encounters;
    private final ArrayList<Observation> observations;

    public Patient(UUID patientId, String fullName, String email, String password, UserType userType,
                   ArrayList<Condition> conditions, ArrayList<Encounter> encounters, ArrayList<Observation> observations) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.conditions = conditions;
        this.encounters = encounters;
        this.observations = observations;
    }

    public Patient(String fullName, String email, String password, UserType userType,
                   ArrayList<Condition> conditions, ArrayList<Encounter> encounters, ArrayList<Observation> observations) {
        this.patientId = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.conditions = conditions;
        this.encounters = encounters;
        this.observations = observations;
    }

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

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public ArrayList<Encounter> getEncounters() {
        return encounters;
    }

    public ArrayList<Observation> getObservations() {
        return observations;
    }

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
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", conditions=" + conditions +
                ", encounters=" + encounters +
                ", observations=" + observations +
                '}';
    }
}
