package api.dto;

import java.util.UUID;

public class PractitionerCreateDTO {
    public String fullName;
    public String email;
    public String password;
    public UUID organizationId;

    public PractitionerCreateDTO() {}

    public PractitionerCreateDTO(String fullName, String email, String password, UUID organizationId) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.organizationId = organizationId;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UUID getOrganizationId() { return organizationId; }
    public void setOrganizationId(UUID organizationId) { this.organizationId = organizationId; }
}
