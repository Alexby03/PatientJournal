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
}
