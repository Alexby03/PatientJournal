package core.mappers;

import api.dto.ObservationDTO;
import data.entities.Observation;
import data.entities.Patient;
import data.entities.Practitioner;
import data.entities.Organization;

public class ObservationMapper {

    public static ObservationDTO toDTO(Observation observation) {
        if (observation == null) return null;

        Patient patient = observation.getPatient();
        Practitioner practitioner = observation.getPractitioner();
        Organization organization = (practitioner != null) ? practitioner.getOrganization() : null;

        String patientName = (patient != null) ? patient.getFullName() : null;
        String practitionerName = (practitioner != null) ? practitioner.getFullName() : null;
        String organizationType = (organization != null) ? organization.getOrganizationType().name() : null;

        return new ObservationDTO(
                observation.getObservationId(),
                observation.getDescription(),
                observation.getObservationDate(),
                patientName,
                practitionerName,
                organizationType
        );
    }
}
