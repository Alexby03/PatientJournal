package api.dto;

import java.time.LocalDateTime;

public class ObservationUpdateDTO {
    public String description;
    public LocalDateTime observationDate;

    public ObservationUpdateDTO() {}

    public ObservationUpdateDTO(String description, LocalDateTime observationDate) {
        this.description = description;
        this.observationDate = observationDate;
    }
}
