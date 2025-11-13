package api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ObservationDTO {
    public UUID observationId;
    public String description;
    public LocalDateTime observationDate;
    public String patientName;
    public String practitionerName;
    public String organizationType;

    public ObservationDTO(UUID observationId, String description, LocalDateTime observationDate,
                          String patientName, String practitionerName, String organizationType) {
        this.observationId = observationId;
        this.description = description;
        this.observationDate = observationDate;
        this.patientName = patientName;
        this.practitionerName = practitionerName;
        this.organizationType = organizationType;
    }
}
