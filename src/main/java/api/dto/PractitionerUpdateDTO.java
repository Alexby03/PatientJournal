package api.dto;

public class PractitionerUpdateDTO {
    public String fullName;
    public String password;

    public PractitionerUpdateDTO() {}

    public PractitionerUpdateDTO(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }
}
