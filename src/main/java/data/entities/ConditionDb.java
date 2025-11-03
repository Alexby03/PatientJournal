package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import core.enums.ConditionType;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "conditions")
public class ConditionDb extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID conditionId;

    @Column(nullable = false)
    public String conditionName;

    @Column(nullable = false)
    public int severityLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ConditionType conditionType;

    @Column(nullable = false)
    public LocalDate diagnosedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    public PatientDb patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    public PractitionerDb doctor;

    // Default constructor
    public ConditionDb() {}

    public ConditionDb(String conditionName, int severityLevel, ConditionType conditionType,
                       LocalDate diagnosedDate, PatientDb patient, PractitionerDb doctor) {
        this.conditionName = conditionName;
        this.severityLevel = severityLevel;
        this.conditionType = conditionType;
        this.diagnosedDate = diagnosedDate;
        this.patient = patient;
        this.doctor = doctor;
    }
}
