package core.services;

import data.entities.Condition;
import data.entities.Patient;
import data.entities.Practitioner;
import data.repositories.ConditionRepository;
import data.repositories.PatientRepository;
import data.repositories.PractitionerRepository;
import api.dto.ConditionCreateDTO;
import api.dto.ConditionUpdateDTO;
import core.enums.ConditionType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ConditionService {

    @Inject
    ConditionRepository conditionRepository;

    @Inject
    PatientRepository patientRepository;

    @Inject
    PractitionerRepository practitionerRepository;

    /**
     * Get all conditions for a patient
     */
    public Uni<List<Condition>> getPatientConditions(UUID patientId) {
        return patientRepository.findById(patientId)
                .chain(patient -> {
                    if (patient == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Patient not found"));
                    }
                    return conditionRepository.findByPatientId(patientId);
                });
    }

    /**
     * Get condition by ID
     */
    public Uni<Condition> getConditionById(UUID conditionId) {
        return conditionRepository.findById(conditionId);
    }

    /**
     * Create a new condition from DTO
     */
    public Uni<Condition> createCondition(UUID patientId, UUID practitionerId, ConditionCreateDTO dto) {
        // Validate DTO
        if (dto.getConditionName() == null || dto.getConditionName().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Condition name is required"));
        }
        if (dto.getSeverityLevel() < 1 || dto.getSeverityLevel() > 10) {
            return Uni.createFrom().failure(new IllegalArgumentException("Severity level must be between 1 and 10"));
        }
        if (dto.getConditionType() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Condition type is required"));
        }
        if (dto.getDiagnosedDate() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Diagnosed date is required"));
        }

        // Validate patient and practitioner exist
        return patientRepository.findById(patientId)
                .chain(patient -> {
                    if (patient == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Patient not found"));
                    }

                    return practitionerRepository.findById(practitionerId)
                            .chain(practitioner -> {
                                if (practitioner == null) {
                                    return Uni.createFrom().failure(
                                            new IllegalArgumentException("Practitioner not found"));
                                }

                                // Create condition entity from DTO
                                Condition condition = new Condition(
                                        dto.getConditionName(),
                                        dto.getSeverityLevel(),
                                        dto.getConditionType(),
                                        dto.getDiagnosedDate(),
                                        patient,
                                        practitioner
                                );

                                return conditionRepository.persist(condition);
                            });
                });
    }

    /**
     * Update a condition from DTO
     */
    public Uni<Condition> updateCondition(UUID conditionId, ConditionUpdateDTO dto) {
        return conditionRepository.findById(conditionId)
                .chain(condition -> {
                    if (condition == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Condition not found"));
                    }

                    if (dto.getConditionName() != null && !dto.getConditionName().isEmpty()) {
                        condition.setConditionName(dto.getConditionName());
                    }
                    if (dto.getSeverityLevel() > 0) {
                        condition.setSeverityLevel(dto.getSeverityLevel());
                    }
                    if (dto.getConditionType() != null) {
                        condition.setConditionType(dto.getConditionType());
                    }
                    if (dto.getDiagnosedDate() != null) {
                        condition.setDiagnosedDate(dto.getDiagnosedDate());
                    }

                    return conditionRepository.persist(condition);
                });
    }

    /**
     * Delete a condition
     */
    public Uni<Boolean> deleteCondition(UUID conditionId) {
        return conditionRepository.deleteById(conditionId);
    }

    /**
     * Get high-severity conditions
     */
    public Uni<List<Condition>> getHighSeverityConditions() {
        return conditionRepository.findHighSeverityConditions();
    }

    /**
     * Get conditions by type
     */
    public Uni<List<Condition>> getConditionsByType(ConditionType conditionType) {
        return conditionRepository.findByConditionType(conditionType);
    }

    /**
     * Count conditions for a patient
     */
    public Uni<Long> countPatientConditions(UUID patientId) {
        return conditionRepository.countByPatient(patientId);
    }
}
