package api.dto;

import java.time.LocalDateTime;

public class EncounterCreateDTO {
    public String description;
    public LocalDateTime encounterDate;

    public EncounterCreateDTO() {}

    public EncounterCreateDTO(String description, LocalDateTime encounterDate) {
        this.description = description;
        this.encounterDate = encounterDate;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getEncounterDate() { return encounterDate; }
    public void setEncounterDate(LocalDateTime encounterDate) { this.encounterDate = encounterDate; }
}
