package core.services;

import data.entities.User;
import data.repositories.UserRepository;
import api.dto.UserCreateDTO;
import api.dto.UserUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    /**
     * Get all users with pagination
     */
    public Uni<List<User>> getAllUsers(int pageIndex, int pageSize) {
        return userRepository.listAllUsers(pageIndex, pageSize);
    }

    /**
     * Get a single user by ID
     */
    public Uni<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    /**
     * Get user by email
     */
    public Uni<User> getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Email cannot be empty"));
        }
        return userRepository.findByEmail(email);
    }

    /**
     * Delete a user
     */
    public Uni<Boolean> deleteUser(UUID userId) {
        return userRepository.deleteById(userId);
    }

    /**
     * Count total users
     */
    public Uni<Long> countUsers() {
        return userRepository.countTotalUsers();
    }

    // TODO: implement proper hashing
    private String hashPassword(String password) {
        return password; // TODO: Keycloadk
    }
}
