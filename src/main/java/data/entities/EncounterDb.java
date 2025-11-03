package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "encounters")
public class EncounterDb extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID encounterId;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public LocalDateTime encounterDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    public PatientDb patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    public PractitionerDb doctor;

    // Default constructor
    public EncounterDb() {}

    public EncounterDb(String description, LocalDateTime encounterDate,
                       PatientDb patient, PractitionerDb doctor) {
        this.description = description;
        this.encounterDate = encounterDate;
        this.patient = patient;
        this.doctor = doctor;
    }
}
