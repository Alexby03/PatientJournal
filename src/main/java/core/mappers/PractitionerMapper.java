package core.mappers;

import api.dto.PractitionerDTO;
import data.entities.Practitioner;
import data.entities.Organization;

public class PractitionerMapper {

    public static PractitionerDTO toDTO(Practitioner practitioner) {
        if (practitioner == null) return null;

        Organization org = practitioner.getOrganization();
        String organizationType = (org != null && org.getOrganizationType() != null)
                ? org.getOrganizationType().name()
                : null;

        return new PractitionerDTO(
                practitioner.getId(),
                practitioner.getFullName(),
                practitioner.getEmail(),
                practitioner.getUserType(),
                organizationType
        );
    }
}
