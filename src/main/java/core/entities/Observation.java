package core.entities;

import java.util.UUID;

public class Observation {

    private final UUID observationId;
    private final UUID doctorId;
    private final UUID patientId;
    private final String description;
    private final String observationDate;

    public Observation(UUID observationId, UUID doctorId, UUID patientId, String description, String observationDate) {
        this.observationId = observationId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.description = description;
        this.observationDate = observationDate;
    }

    public Observation(UUID doctorId, UUID patientId, String description, String observationDate) {
        this.observationId = UUID.randomUUID();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.description = description;
        this.observationDate = observationDate;
    }

    public UUID getObservationId() {
        return observationId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public String getDescription() {
        return description;
    }

    public String getObservationDate() {
        return observationDate;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "observationId=" + observationId +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", description='" + description + '\'' +
                ", observationDate='" + observationDate + '\'' +
                '}';
    }
}
