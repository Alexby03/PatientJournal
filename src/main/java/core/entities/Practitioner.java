package core.entities;

import core.enums.UserType;

import java.util.UUID;

public class Practitioner {

    private final UUID doctorId;
    private String fullName;
    private String email;
    private String password;
    private final UserType userType;
    private Organization organization;

    public Practitioner(UUID doctorId, String fullName, String email, String password, UserType userType, Organization organization) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.organization = organization;
    }

    public Practitioner(String fullName, String email, String password, UserType userType, Organization organization) {
        this.doctorId = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.organization = organization;
    }

    public UUID getDoctorId() {
        return doctorId;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "Practitioner{" +
                "doctorId=" + doctorId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", organization=" + organization +
                '}';
    }
}
