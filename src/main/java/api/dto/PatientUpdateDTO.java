package api.dto;

public class PatientUpdateDTO {
    public String fullName;
    public String password;

    public PatientUpdateDTO() {}

    public PatientUpdateDTO(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }
}
