package core.services;

import data.entities.Patient;
import data.repositories.PatientRepository;
import api.dto.PatientCreateDTO;
import api.dto.PatientUpdateDTO;
import core.enums.UserType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PatientService {

    @Inject
    PatientRepository patientRepository;

    /**
     * Get all patients with pagination
     */
    public Uni<List<Patient>> getAllPatients(int pageIndex, int pageSize) {
        return patientRepository.findAllPatients(pageIndex, pageSize);
    }

    /**
     * Get patient by ID
     */
    public Uni<Patient> getPatientById(UUID patientId) {
        return patientRepository.findById(patientId);
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
     * Create a new patient from DTO
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

                    return patientRepository.persist(patient);
                });
    }

    /**
     * Update patient information from DTO
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
     * Search patients by name
     */
    public Uni<List<Patient>> searchPatients(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Search term cannot be empty"));
        }
        return patientRepository.searchByName(searchTerm);
    }

    /**
     * Delete a patient
     */
    public Uni<Boolean> deletePatient(UUID patientId) {
        return patientRepository.deleteById(patientId);
    }

    /**
     * Count total patients
     */
    public Uni<Long> countPatients() {
        return patientRepository.countPatients();
    }

    private String hashPassword(String password) {
        return password; // TODO: Keycloak
    }
}
