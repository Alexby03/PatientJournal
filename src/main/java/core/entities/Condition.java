package core.entities;

import core.enums.ConditionType;
import java.time.LocalDate;
import java.util.UUID;

public class Condition {

    private final UUID conditionId;
    private final String conditionName;
    private final int severityLevel;
    private final ConditionType conditionType;
    private final LocalDate diagnosedDate;
    private final UUID doctorId; // new field

    public Condition(UUID conditionId, String conditionName, int severityLevel,
                     ConditionType conditionType, LocalDate diagnosedDate, UUID doctorId) {
        this.conditionId = conditionId;
        this.conditionName = conditionName;
        this.severityLevel = severityLevel;
        this.conditionType = conditionType;
        this.diagnosedDate = diagnosedDate;
        this.doctorId = doctorId;
    }

    public Condition(String conditionName, int severityLevel,
                     ConditionType conditionType, LocalDate diagnosedDate, UUID doctorId) {
        this.conditionId = UUID.randomUUID();
        this.conditionName = conditionName;
        this.severityLevel = severityLevel;
        this.conditionType = conditionType;
        this.diagnosedDate = diagnosedDate;
        this.doctorId = doctorId;
    }

    public UUID getConditionId() { return conditionId; }
    public String getConditionName() { return conditionName; }
    public int getSeverityLevel() { return severityLevel; }
    public ConditionType getConditionType() { return conditionType; }
    public LocalDate getDiagnosedDate() { return diagnosedDate; }
    public UUID getDoctorId() { return doctorId; }

    @Override
    public String toString() {
        return "Condition{" +
                "conditionId=" + conditionId +
                ", conditionName='" + conditionName + '\'' +
                ", severityLevel=" + severityLevel +
                ", conditionType=" + conditionType +
                ", diagnosedDate=" + diagnosedDate +
                ", doctorId=" + doctorId +
                '}';
    }
}
