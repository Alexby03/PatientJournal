package api.dto;

public class UserUpdateDTO {
    public String fullName;
    public String password; // email immutable

    public UserUpdateDTO() {}

    public UserUpdateDTO(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }
}
