package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "encounters")
public class Encounter extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID encounterId;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public LocalDateTime encounterDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    public Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    public Practitioner doctor;

    // Default constructor
    public Encounter() {}

    public Encounter(String description, LocalDateTime encounterDate,
                     Patient patient, Practitioner doctor) {
        this.description = description;
        this.encounterDate = encounterDate;
        this.patient = patient;
        this.doctor = doctor;
    }
}
