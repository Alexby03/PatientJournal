package data.repositories;

import data.entities.Observation;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ObservationRepository implements PanacheRepositoryBase<Observation, UUID> {

    /**
     * Find observations by patient ID
     */
    public Uni<List<Observation>> findByPatientId(UUID patientId) {
        return find("patient.id", patientId).list();
    }

    /**
     * Find observations by practitioner ID
     */
    public Uni<List<Observation>> findByPractitionerId(UUID practitionerId) {
        return find("practitioner.id", practitionerId).list();
    }

    /**
     * Find observations within a date range
     */
    public Uni<List<Observation>> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return find("observationDate between ?1 and ?2", startDate, endDate).list();
    }

    /**
     * Search observations by description
     */
    public Uni<List<Observation>> searchByDescription(String descriptionPattern) {
        return find("description like ?1", "%" + descriptionPattern + "%").list();
    }

    /**
     * Count observations for a patient
     */
    public Uni<Long> countByPatient(UUID patientId) {
        return count("patient.id", patientId);
    }

    /**
     * Get most recent observation for a patient
     */
    public Uni<Observation> findMostRecentByPatient(UUID patientId) {
        return find("patient.id", patientId).list()
                .onItem().transform(list -> list.stream()
                        .max(Comparator.comparing(Observation::getObservationDate))
                        .orElse(null)
                );
    }

    /**
     * Get observations with pagination
     */
    public Uni<List<Observation>> findPatientObservationsPaginated(UUID patientId, int pageIndex, int pageSize) {
        return find("patient.id", patientId).page(pageIndex, pageSize).list();
    }
}
