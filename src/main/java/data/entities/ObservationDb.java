package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "observations")
public class ObservationDb extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID observationId;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public LocalDateTime observationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    public PatientDb patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    public PractitionerDb doctor;

    // Default constructor
    public ObservationDb() {}

    public ObservationDb(String description, LocalDateTime observationDate,
                         PatientDb patient, PractitionerDb doctor) {
        this.description = description;
        this.observationDate = observationDate;
        this.patient = patient;
        this.doctor = doctor;
    }
}
