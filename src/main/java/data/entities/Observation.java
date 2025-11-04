package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "observations")
public class Observation extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID observationId;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public LocalDateTime observationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    public Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    public Practitioner doctor;

    // Default constructor
    public Observation() {}

    public Observation(String description, LocalDateTime observationDate,
                       Patient patient, Practitioner doctor) {
        this.description = description;
        this.observationDate = observationDate;
        this.patient = patient;
        this.doctor = doctor;
    }
}
