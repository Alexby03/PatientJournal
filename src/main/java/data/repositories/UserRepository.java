package data.repositories;

import data.entities.User;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, UUID> {

    /**
     * Find a user by email address
     */
    public Uni<User> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    /**
     * Find a user by full name
     */
    public Uni<User> findByFullName(String fullName) {
        return find("fullName", fullName).firstResult();
    }

    /**
     * List all users with pagination
     */
    public Uni<List<User>> listAllUsers(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize).list();
    }

    /**
     * Count total users
     */
    public Uni<Long> countTotalUsers() {
        return count();
    }

    /**
     * Delete user by email
     */
    public Uni<Boolean> deleteByEmail(String email) {
        return delete("email", email).map(count -> count > 0);
    }
}
