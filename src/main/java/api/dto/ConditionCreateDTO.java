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
}
