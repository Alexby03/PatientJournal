package core.mappers;

import api.dto.PatientDTO;
import data.entities.Patient;

public class PatientMapper {

    public static PatientDTO toDTO(Patient patient) {
        if (patient == null) return null;

        return new PatientDTO(
                patient.getId(),
                patient.getFullName(),
                patient.getEmail()
        );
    }
}
