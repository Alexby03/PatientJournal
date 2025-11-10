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
    private UUID observationId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime observationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    // Default constructor
    public Observation() {}

    public Observation(String description, LocalDateTime observationDate,
                       Patient patient, Practitioner doctor) {
        this.description = description;
        this.observationDate = observationDate;
        this.patient = patient;
        this.practitioner = doctor;
    }

    public UUID getObservationId() {
        return observationId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getObservationDate() {
        return observationDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "observationId=" + observationId +
                ", description='" + description + '\'' +
                ", observationDate=" + observationDate +
                ", patient=" + patient +
                ", practitioner=" + practitioner+
                '}';
    }
}
