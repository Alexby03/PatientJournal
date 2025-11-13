package data.repositories;

import data.entities.User;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, UUID> {

    public Uni<User> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Uni<User> findByFullName(String fullName) {
        return find("fullName", fullName).firstResult();
    }

    public Uni<List<User>> listAllUsers(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize).list();
    }

    public Uni<Long> countTotalUsers() {
        return count();
    }

    public Uni<Boolean> deleteByEmail(String email) {
        return delete("email", email).map(count -> count > 0);
    }
}
