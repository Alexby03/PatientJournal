package core.mappers;

import api.dto.ConditionDTO;
import data.entities.Condition;
import data.entities.Patient;
import data.entities.Practitioner;

public class ConditionMapper {

    public static ConditionDTO toDTO(Condition condition) {
        if (condition == null) return null;

        Patient patient = condition.getPatient();
        Practitioner practitioner = condition.getPractitioner();

        String patientName = (patient != null) ? patient.getFullName() : null;
        String practitionerName = (practitioner != null) ? practitioner.getFullName() : null;

        return new ConditionDTO(
                condition.getConditionId(),
                condition.getConditionName(),
                condition.getConditionType(),
                condition.getSeverityLevel(),
                condition.getDiagnosedDate(),
                patientName,
                practitionerName
        );
    }
}
