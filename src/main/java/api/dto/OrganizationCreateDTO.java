package api.dto;

import core.enums.OrganizationType;
import java.util.UUID;

public class OrganizationCreateDTO {
    public OrganizationType organizationType;
    public UUID locationId;

    public OrganizationCreateDTO() {}

    public OrganizationCreateDTO(OrganizationType organizationType, UUID locationId) {
        this.organizationType = organizationType;
        this.locationId = locationId;
    }

    public OrganizationType getOrganizationType() { return organizationType; }
    public void setOrganizationType(OrganizationType organizationType) { this.organizationType = organizationType; }

    public UUID getLocationId() { return locationId; }
    public void setLocationId(UUID locationId) { this.locationId = locationId; }
}
