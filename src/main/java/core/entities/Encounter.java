package core.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Encounter {

    private final UUID encounterId;
    private final UUID doctorId;
    private final UUID patientId;
    private final String description;
    private final LocalDateTime encounterDate;

    public Encounter(UUID encounterId, UUID doctorId, UUID patientId, String description, LocalDateTime encounterDate) {
        this.encounterId = encounterId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.description = description;
        this.encounterDate = encounterDate;
    }

    public Encounter(UUID doctorId, UUID patientId, String description, LocalDateTime encounterDate) {
        this.encounterId = UUID.randomUUID();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.description = description;
        this.encounterDate = encounterDate;
    }

    public UUID getEncounterId() {
        return encounterId;
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

    public LocalDateTime getEncounterDate() {
        return encounterDate;
    }

    @Override
    public String toString() {
        return "Encounter{" +
                "encounterId=" + encounterId +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", description='" + description + '\'' +
                ", encounterDate=" + encounterDate +
                '}';
    }
}
