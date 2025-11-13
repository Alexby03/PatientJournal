package api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class EncounterDTO {
    public UUID encounterId;
    public LocalDateTime encounterDate;
    public String notes;
    public String patientName;
    public String practitionerName;
    public String organizationName;

    public EncounterDTO(UUID encounterId, LocalDateTime encounterDate, String notes,
                        String patientName, String practitionerName, String organizationName) {
        this.encounterId = encounterId;
        this.encounterDate = encounterDate;
        this.notes = notes;
        this.patientName = patientName;
        this.practitionerName = practitionerName;
        this.organizationName = organizationName;
    }
}