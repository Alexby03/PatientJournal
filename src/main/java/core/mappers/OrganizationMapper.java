package core.mappers;

import api.dto.OrganizationDTO;
import data.entities.Organization;

public class OrganizationMapper {

    public static OrganizationDTO toDTO(Organization organization) {
        if (organization == null) return null;
        String locationType = organization.getLocation() != null ?
                organization.getLocation().getLocationType().name() : null;

        return new OrganizationDTO(
                organization.getOrganizationId(),
                organization.getOrganizationType(),
                locationType
        );
    }
}
