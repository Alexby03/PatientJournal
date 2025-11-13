package data.repositories;

import data.entities.Condition;
import data.entities.Patient;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PatientRepository implements PanacheRepositoryBase<Patient, UUID> {

    /**
     * Find patient by email
     */
    public Uni<Patient> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    /**
     * Find all patients with pagination
     */
    public Uni<List<Patient>> findAllPatients(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize).list();
    }

    /**
     * Search patients by name with pagination
     */
    public Uni<List<Patient>> searchByName(String namePattern, int pageIndex, int pageSize) {
        return find("fullName like ?1", "%" + namePattern + "%")
                .page(pageIndex, pageSize)
                .list();
    }

    /**
     * Find patient by ID with all relations
     */
    public Uni<Patient> findByIdWithRelations(UUID id) {
        return find("""
            SELECT p
            FROM Patient p
            LEFT JOIN FETCH p.conditions
            LEFT JOIN FETCH p.encounters
            LEFT JOIN FETCH p.observations
            WHERE p.id = ?1
        """, id).firstResult();
    }

    /**
     * Delete patient by email
     */
    public Uni<Boolean> deletePatientByEmail(String email) {
        return delete("email", email).map(count -> count > 0);
    }
}
