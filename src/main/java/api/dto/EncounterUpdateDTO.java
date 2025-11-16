package api.dto;

import java.time.LocalDateTime;

public class EncounterUpdateDTO {
    public String description;
    public LocalDateTime encounterDate;

    public EncounterUpdateDTO() {}

    public EncounterUpdateDTO(String description, LocalDateTime encounterDate) {
        this.description = description;
        this.encounterDate = encounterDate;
    }
}
