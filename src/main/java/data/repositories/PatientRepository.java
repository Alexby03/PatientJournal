package data.repositories;

import data.entities.Patient;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<Patient> {

    /**
     * Find patient by email
     */
    public Uni<Patient> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    /**
     * Find patient by full name
     */
    public Uni<Patient> findByFullName(String fullName) {
        return find("fullName", fullName).firstResult();
    }

    /**
     * Get all patients with pagination
     */
    public Uni<List<Patient>> findAllPatients(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize).list();
    }

    /**
     * Count total patients
     */
    public Uni<Long> countPatients() {
        return count();
    }

    /**
     * Find patients by partial name match (search functionality)
     */
    public Uni<List<Patient>> searchByName(String namePattern) {
        return find("fullName like ?1", "%" + namePattern + "%").list();
    }

    /**
     * Delete patient by email
     */
    public Uni<Boolean> deletePatientByEmail(String email) {
        return delete("email", email).map(count -> count > 0);
    }
}
