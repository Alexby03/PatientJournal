package core.services;

import data.entities.Observation;
import data.entities.Patient;
import data.entities.Practitioner;
import data.repositories.ObservationRepository;
import data.repositories.PatientRepository;
import data.repositories.PractitionerRepository;
import api.dto.ObservationCreateDTO;
import api.dto.ObservationUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ObservationService {

    @Inject
    ObservationRepository observationRepository;

    @Inject
    PatientRepository patientRepository;

    @Inject
    PractitionerRepository practitionerRepository;

    /**
     * Get all observations for a patient
     */
    public Uni<List<Observation>> getPatientObservations(UUID patientId) {
        return patientRepository.findById(patientId)
                .chain(patient -> {
                    if (patient == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Patient not found"));
                    }
                    return observationRepository.findByPatientId(patientId);
                });
    }

    /**
     * Get observation by ID
     */
    public Uni<Observation> getObservationById(UUID observationId) {
        return observationRepository.findById(observationId);
    }

    /**
     * Create a new observation from DTO
     */
    public Uni<Observation> createObservation(UUID patientId, UUID practitionerId, ObservationCreateDTO dto) {
        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Description is required"));
        }
        if (dto.getObservationDate() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Observation date is required"));
        }

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

                                Observation observation = new Observation(
                                        dto.getDescription(),
                                        dto.getObservationDate(),
                                        patient,
                                        practitioner
                                );

                                return observationRepository.persist(observation);
                            });
                });
    }

    /**
     * Update an observation from DTO
     */
    public Uni<Observation> updateObservation(UUID observationId, ObservationUpdateDTO dto) {
        return observationRepository.findById(observationId)
                .chain(observation -> {
                    if (observation == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Observation not found"));
                    }

                    if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
                        observation.setDescription(dto.getDescription());
                    }
                    if (dto.getObservationDate() != null) {
                        observation.setObservationDate(dto.getObservationDate());
                    }

                    return observationRepository.persist(observation);
                });
    }

    /**
     * Delete an observation
     */
    public Uni<Boolean> deleteObservation(UUID observationId) {
        return observationRepository.deleteById(observationId);
    }

    /**
     * Get most recent observation for a patient
     */
    public Uni<Observation> getMostRecentObservation(UUID patientId) {
        return observationRepository.findByPatientId(patientId)
                .map(observations -> {
                    if (observations == null || observations.isEmpty()) {
                        return null;
                    }
                    return observations.stream()
                            .max((o1, o2) -> o1.getObservationDate().compareTo(o2.getObservationDate()))
                            .orElse(null);
                });
    }

    /**
     * Get observations by practitioner
     */
    public Uni<List<Observation>> getPractitionerObservations(UUID practitionerId) {
        return observationRepository.findByPractitionerId(practitionerId);
    }

    /**
     * Count observations for a patient
     */
    public Uni<Long> countPatientObservations(UUID patientId) {
        return observationRepository.countByPatient(patientId);
    }
}
