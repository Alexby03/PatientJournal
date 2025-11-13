package api.dto;

import java.util.UUID;

public class PatientDTO {
    public UUID id;
    public String fullName;
    public String email;

    public PatientDTO(UUID id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }
}
