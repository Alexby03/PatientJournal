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
    public Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    public Practitioner doctor;

    // Default constructor
    public Condition() {}

    public Condition(String conditionName, int severityLevel, ConditionType conditionType,
                     LocalDate diagnosedDate, Patient patient, Practitioner doctor) {
        this.conditionName = conditionName;
        this.severityLevel = severityLevel;
        this.conditionType = conditionType;
        this.diagnosedDate = diagnosedDate;
        this.patient = patient;
        this.doctor = doctor;
    }
}
