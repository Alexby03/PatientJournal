package api.dto;

import core.enums.ConditionType;
import java.time.LocalDate;
import java.util.UUID;

public class ConditionDTO {
    public UUID conditionId;
    public String conditionName;
    public ConditionType conditionType;
    public int severityLevel;
    public LocalDate diagnosedDate;
    public String practitionerName;
    public String patientName;

    public ConditionDTO(UUID conditionId, String conditionName, ConditionType conditionType,
                        int severityLevel, LocalDate diagnosedDate, String practitionerName, String patientName) {
        this.conditionId = conditionId;
        this.conditionName = conditionName;
        this.conditionType = conditionType;
        this.severityLevel = severityLevel;
        this.diagnosedDate = diagnosedDate;
        this.practitionerName = practitionerName;
        this.patientName = patientName;
    }
}
