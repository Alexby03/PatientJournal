package api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import api.dto.ConditionDTO;
import api.dto.EncounterDTO;
import api.dto.ObservationDTO;

public class PatientDTO {
    public UUID id;
    public String fullName;
    public String email;

    public List<ConditionDTO> conditions = new ArrayList<>();
    public List<EncounterDTO> encounters = new ArrayList<>();
    public List<ObservationDTO> observations = new ArrayList<>();

    public PatientDTO(UUID id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }
}
