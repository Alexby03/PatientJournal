package api.dto;

import core.enums.UserType;
import java.util.UUID;

public class PractitionerDTO {
    public UUID id;
    public String fullName;
    public String email;
    public UserType userType;
    public String organizationType;

    public PractitionerDTO(UUID id, String fullName, String email, UserType userType, String organizationType) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.userType = userType;
        this.organizationType = organizationType;
    }
}
