package api.dto;

import core.enums.UserType;

public class UserUpdateDTO {
    public String fullName;
    public String password; // email should be immutable

    public UserUpdateDTO() {}

    public UserUpdateDTO(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
