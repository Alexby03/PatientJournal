package api.dto;

import core.enums.ConditionType;
import java.time.LocalDate;

public class ConditionCreateDTO {
    public String conditionName;
    public int severityLevel;
    public ConditionType conditionType;
    public LocalDate diagnosedDate;

    public ConditionCreateDTO() {}

    public ConditionCreateDTO(String conditionName, int severityLevel, ConditionType conditionType, LocalDate diagnosedDate) {
        this.conditionName = conditionName;
        this.severityLevel = severityLevel;
        this.conditionType = conditionType;
        this.diagnosedDate = diagnosedDate;
    }

    public String getConditionName() { return conditionName; }
    public void setConditionName(String conditionName) { this.conditionName = conditionName; }

    public int getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(int severityLevel) { this.severityLevel = severityLevel; }

    public ConditionType getConditionType() { return conditionType; }
    public void setConditionType(ConditionType conditionType) { this.conditionType = conditionType; }

    public LocalDate getDiagnosedDate() { return diagnosedDate; }
    public void setDiagnosedDate(LocalDate diagnosedDate) { this.diagnosedDate = diagnosedDate; }
}
