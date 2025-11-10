package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import core.enums.ConditionType;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "conditions")
public class Condition extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID conditionId;

    @Column(nullable = false)
    private String conditionName;

    @Column(nullable = false)
    private int severityLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConditionType conditionType;

    @Column(nullable = false)
    private LocalDate diagnosedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    public Condition() {}

    public Condition(String conditionName, int severityLevel, ConditionType conditionType,
                     LocalDate diagnosedDate, Patient patient, Practitioner practitioner) {
        this.conditionName = conditionName;
        this.severityLevel = severityLevel;
        this.conditionType = conditionType;
        this.diagnosedDate = diagnosedDate;
        this.patient = patient;
        this.practitioner = practitioner;
    }

    public UUID getConditionId() {
        return conditionId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public LocalDate getDiagnosedDate() {
        return diagnosedDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "conditionId=" + conditionId +
                ", conditionName='" + conditionName + '\'' +
                ", severityLevel=" + severityLevel +
                ", conditionType=" + conditionType +
                ", diagnosedDate=" + diagnosedDate +
                ", patient=" + patient +
                ", practitioner=" + practitioner +
                '}';
    }
}
