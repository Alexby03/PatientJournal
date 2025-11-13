package core.services;

import api.dto.*;
import core.mappers.ConditionMapper;
import core.mappers.EncounterMapper;
import core.mappers.ObservationMapper;
import core.mappers.PatientMapper;
import data.entities.Condition;
import data.entities.Encounter;
import data.entities.Observation;
import data.entities.Patient;
import data.repositories.ConditionRepository;
import data.repositories.EncounterRepository;
import data.repositories.ObservationRepository;
import data.repositories.PatientRepository;
import core.enums.UserType;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PatientService {

    @Inject
    PatientRepository patientRepository;
    @Inject
    ConditionRepository conditionRepository;
    @Inject
    EncounterRepository encounterRepository;
    @Inject
    ObservationRepository observationRepository;

    /**
     * Get all patients with pagination
     */
    public Uni<List<Patient>> getAllPatients(int pageIndex, int pageSize) {
        return patientRepository.findAllPatients(pageIndex, pageSize);
    }

    /**
     * Get patient by ID
     * Use eager fetch if you need relations, annars vanlig findById räcker
     */
    public Uni<PatientDTO> getPatientById(UUID patientId, boolean fetchRelations) {
        if (!fetchRelations) {
            return patientRepository.findById(patientId)
                    .map(PatientMapper::toDTO);
        }

        return patientRepository.findById(patientId)
            .chain(patient -> {
                if (patient == null) return Uni.createFrom().nullItem();

                Uni<List<ConditionDTO>> conditions = conditionRepository.findByPatientId(patientId)
                        .map(list -> list.stream().map(ConditionMapper::toDTO).toList());
                Uni<List<EncounterDTO>> encounters = encounterRepository.findByPatientId(patientId)
                        .map(list -> list.stream().map(EncounterMapper::toDTO).toList());
                Uni<List<ObservationDTO>> observations = observationRepository.findByPatientId(patientId)
                        .map(list -> list.stream().map(ObservationMapper::toDTO).toList());

                return Uni.combine().all().unis(conditions, encounters, observations)
                    .asTuple()
                    .map(tuple -> {
                        PatientDTO dto = PatientMapper.toDTO(patient);
                        dto.conditions.addAll(tuple.getItem1());
                        dto.encounters.addAll(tuple.getItem2());
                        dto.observations.addAll(tuple.getItem3());
                        return dto;
                    });
            });
    }


    /**
     * Get patient by email
     */
    public Uni<Patient> getPatientByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Email cannot be empty"));
        }
        return patientRepository.findByEmail(email);
    }

    /**
     * Search patients by name (partial match)
     */
    public Uni<List<Patient>> searchPatientsByName(String namePattern, int pageIndex, int pageSize) {
        if (namePattern == null || namePattern.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Search term cannot be empty"));
        }
        return patientRepository.searchByName(namePattern, pageIndex, pageSize);
    }

    /**
     * Create a new patient
     */
    public Uni<Patient> createPatient(PatientCreateDTO dto) {
        if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Full name is required"));
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Email is required"));
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Password is required"));
        }

        return patientRepository.findByEmail(dto.getEmail())
                .chain(existing -> {
                    if (existing != null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("User with same e-mail already exists"));
                    }

                    Patient patient = new Patient();
                    patient.setFullName(dto.getFullName());
                    patient.setEmail(dto.getEmail());
                    patient.setPassword(hashPassword(dto.getPassword()));
                    patient.setUserType(UserType.Patient);

                    return Panache.withTransaction(() ->  patientRepository.persist(patient));
                });
    }

    /**
     * Update patient information
     */
    public Uni<Patient> updatePatient(UUID patientId, PatientUpdateDTO dto) {
        return patientRepository.findById(patientId)
                .chain(patient -> {
                    if (patient == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Patient not found"));
                    }

                    if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
                        patient.setFullName(dto.getFullName());
                    }
                    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                        patient.setPassword(hashPassword(dto.getPassword()));
                    }

                    return patientRepository.persist(patient);
                });
    }

    /**
     * Delete a patient by ID
     */
    public Uni<Boolean> deletePatient(UUID patientId) {
        return patientRepository.deleteById(patientId);
    }

    /**
     * Count total patients
     */
    public Uni<Long> countPatients() {
        return patientRepository.count();
    }

    private String hashPassword(String password) {
        return password; // TODO: Keycloak
    }
}
