package api.dto;

public class PatientUpdateDTO {
    public String fullName;
    public String password;

    public PatientUpdateDTO() {}

    public PatientUpdateDTO(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
