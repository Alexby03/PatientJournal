package data.repositories;

import data.entities.Patient;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

public class PatientRepository implements PanacheRepository<Patient> {

    // Example custom method
    public Uni<Patient> findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
