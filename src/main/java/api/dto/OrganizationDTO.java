package api.dto;

import core.enums.OrganizationType;
import java.util.UUID;

public class OrganizationDTO {

    public UUID organizationId;
    public OrganizationType organizationType;
    public String locationType;

    public OrganizationDTO(UUID organizationId, OrganizationType organizationType, String locationType) {
        this.organizationId = organizationId;
        this.organizationType = organizationType;
        this.locationType = locationType;
    }
}