package api.dto;

import core.enums.OrganizationType;
import java.util.UUID;

public class OrganizationUpdateDTO {
    public OrganizationType organizationType;
    public UUID locationId;

    public OrganizationUpdateDTO() {}

    public OrganizationUpdateDTO(OrganizationType organizationType, UUID locationId) {
        this.organizationType = organizationType;
        this.locationId = locationId;
    }
}
