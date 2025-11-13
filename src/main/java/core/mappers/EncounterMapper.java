package core.mappers;

import api.dto.EncounterDTO;
import data.entities.Encounter;
import data.entities.Patient;
import data.entities.Practitioner;
import data.entities.Organization;

public class EncounterMapper {

    public static EncounterDTO toDTO(Encounter encounter) {
        if (encounter == null) return null;

        Patient patient = encounter.getPatient();
        Practitioner practitioner = encounter.getPractitioner();
        Organization organization = (practitioner != null) ? practitioner.getOrganization() : null;

        String patientName = (patient != null) ? patient.getFullName() : null;
        String practitionerName = (practitioner != null) ? practitioner.getFullName() : null;
        String organizationName = (organization != null) ? organization.getOrganizationType().name() : null;

        return new EncounterDTO(
                encounter.getEncounterId(),
                encounter.getEncounterDate(),
                encounter.getDescription(),
                patientName,
                practitionerName,
                organizationName
        );
    }
}
