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
}
