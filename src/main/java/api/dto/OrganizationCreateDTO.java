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
}
