package api.dto;

import java.util.UUID;
import core.enums.UserType;

public class UserLoginDTO {
    public UUID id;
    public UserType userType;
    public String homePage;

    public UserLoginDTO(UUID id, UserType userType, String homePage) {
        this.id = id;
        this.userType = userType;
        this.homePage = homePage;
    }
}
