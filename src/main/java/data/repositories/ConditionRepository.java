package data.repositories;

import data.entities.Condition;
import core.enums.ConditionType;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ConditionRepository implements PanacheRepository<Condition> {

    /**
     * Find conditions by patient ID
     */
    public Uni<List<Condition>> findByPatientId(UUID patientId) {
        return find("patient.id", patientId).list();
    }

    /**
     * Find conditions by condition type
     */
    public Uni<List<Condition>> findByConditionType(ConditionType conditionType) {
        return find("conditionType", conditionType).list();
    }

    /**
     * Find conditions by severity level
     */
    public Uni<List<Condition>> findBySeverityLevel(int severityLevel) {
        return find("severityLevel", severityLevel).list();
    }

    /**
     * Find conditions diagnosed by a specific practitioner
     */
    public Uni<List<Condition>> findByPractitionerId(UUID practitionerId) {
        return find("practitioner.id", practitionerId).list();
    }

    /**
     * Get high-severity conditions (severity >= 7)
     */
    public Uni<List<Condition>> findHighSeverityConditions() {
        return find("severityLevel >= ?1", 7).list();
    }

    /**
     * Search conditions by condition name
     */
    public Uni<List<Condition>> searchByName(String namePattern) {
        return find("conditionName like ?1", "%" + namePattern + "%").list();
    }

    /**
     * Count conditions for a patient
     */
    public Uni<Long> countByPatient(UUID patientId) {
        return count("patient.id", patientId);
    }

    /**
     * Get paginated conditions for a patient
     */
    public Uni<List<Condition>> findPatientConditionsPaginated(UUID patientId, int pageIndex, int pageSize) {
        return find("patient.id", patientId).page(pageIndex, pageSize).list();
    }
}
