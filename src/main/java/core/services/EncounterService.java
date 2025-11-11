package core.services;

import data.entities.Encounter;
import data.entities.Patient;
import data.entities.Practitioner;
import data.repositories.EncounterRepository;
import data.repositories.PatientRepository;
import data.repositories.PractitionerRepository;
import api.dto.EncounterCreateDTO;
import api.dto.EncounterUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EncounterService {

    @Inject
    EncounterRepository encounterRepository;

    @Inject
    PatientRepository patientRepository;

    @Inject
    PractitionerRepository practitionerRepository;

    /**
     * Get all encounters for a patient
     */
    public Uni<List<Encounter>> getPatientEncounters(UUID patientId) {
        return patientRepository.findById(patientId)
                .chain(patient -> {
                    if (patient == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Patient not found"));
                    }
                    return encounterRepository.findByPatientId(patientId);
                });
    }

    /**
     * Get encounter by ID
     */
    public Uni<Encounter> getEncounterById(UUID encounterId) {
        return encounterRepository.findById(encounterId);
    }

    /**
     * Create a new encounter from DTO
     */
    public Uni<Encounter> createEncounter(UUID patientId, UUID practitionerId, EncounterCreateDTO dto) {
        // Validate DTO
        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Description is required"));
        }
        if (dto.getEncounterDate() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Encounter date is required"));
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

                                // Create encounter entity from DTO
                                Encounter encounter = new Encounter(
                                        dto.getDescription(),
                                        dto.getEncounterDate(),
                                        patient,
                                        practitioner
                                );

                                return encounterRepository.persist(encounter);
                            });
                });
    }

    /**
     * Update an encounter from DTO
     */
    public Uni<Encounter> updateEncounter(UUID encounterId, EncounterUpdateDTO dto) {
        return encounterRepository.findById(encounterId)
                .chain(encounter -> {
                    if (encounter == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Encounter not found"));
                    }

                    if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
                        encounter.setDescription(dto.getDescription());
                    }
                    if (dto.getEncounterDate() != null) {
                        encounter.setEncounterDate(dto.getEncounterDate());
                    }

                    return encounterRepository.persist(encounter);
                });
    }

    /**
     * Delete an encounter
     */
    public Uni<Boolean> deleteEncounter(UUID encounterId) {
        return encounterRepository.deleteById(encounterId);
    }

    /**
     * Get recent encounters (last 30 days)
     */
    public Uni<List<Encounter>> getRecentEncounters() {
        return encounterRepository.findRecentEncounters();
    }

    /**
     * Get encounters by practitioner
     */
    public Uni<List<Encounter>> getPractitionerEncounters(UUID practitionerId) {
        return encounterRepository.findByPractitionerId(practitionerId);
    }

    /**
     * Count encounters for a patient
     */
    public Uni<Long> countPatientEncounters(UUID patientId) {
        return encounterRepository.countByPatient(patientId);
    }
}
