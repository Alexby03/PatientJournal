package api.dto;

import java.time.LocalDateTime;

public class ObservationCreateDTO {
    public String description;
    public LocalDateTime observationDate;

    public ObservationCreateDTO() {}

    public ObservationCreateDTO(String description, LocalDateTime observationDate) {
        this.description = description;
        this.observationDate = observationDate;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getObservationDate() { return observationDate; }
    public void setObservationDate(LocalDateTime observationDate) { this.observationDate = observationDate; }
}
