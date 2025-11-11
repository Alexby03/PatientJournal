package data.repositories;

import data.entities.Encounter;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EncounterRepository implements PanacheRepository<Encounter> {

    /**
     * Find encounters by patient ID
     */
    public Uni<List<Encounter>> findByPatientId(UUID patientId) {
        return find("patient.id", patientId).list();
    }

    /**
     * Find encounters by practitioner ID
     */
    public Uni<List<Encounter>> findByPractitionerId(UUID practitionerId) {
        return find("practitioner.id", practitionerId).list();
    }

    /**
     * Find encounters within a date range
     */
    public Uni<List<Encounter>> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return find("encounterDate between ?1 and ?2", startDate, endDate).list();
    }

    /**
     * Get recent encounters (last 30 days)
     */
    public Uni<List<Encounter>> findRecentEncounters() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return find("encounterDate >= ?1", thirtyDaysAgo).list();
    }

    /**
     * Search encounters by description
     */
    public Uni<List<Encounter>> searchByDescription(String descriptionPattern) {
        return find("description like ?1", "%" + descriptionPattern + "%").list();
    }

    /**
     * Count encounters for a patient
     */
    public Uni<Long> countByPatient(UUID patientId) {
        return count("patient.id", patientId);
    }

    /**
     * Get encounters with pagination
     */
    public Uni<List<Encounter>> findPatientEncountersPaginated(UUID patientId, int pageIndex, int pageSize) {
        return find("patient.id", patientId).page(pageIndex, pageSize).list();
    }

    /**
     * Delete encounters older than a specific date
     */
    public Uni<Boolean> deleteOlderThan(LocalDateTime cutoffDate) {
        return delete("encounterDate < ?1", cutoffDate).map(count -> count > 0);
    }
}
