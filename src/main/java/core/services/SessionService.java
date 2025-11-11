package core.services;

import data.entities.Session;
import data.entities.User;
import data.repositories.SessionRepository;
import data.repositories.UserRepository;
import api.dto.SessionCreateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SessionService {

    @Inject
    SessionRepository sessionRepository;

    @Inject
    UserRepository userRepository;

    /**
     * Get all sessions for a user
     */
    public Uni<List<Session>> getUserSessions(UUID userId) {
        return userRepository.findById(userId)
                .chain(user -> {
                    if (user == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("User not found"));
                    }
                    return sessionRepository.findAllUserSessions(userId);
                });
    }

    /**
     * Get session by ID
     */
    public Uni<Session> getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }

    /**
     * Create a new session from DTO
     */
    public Uni<Session> createSession(SessionCreateDTO dto) {
        // Validate DTO
        if (dto.getSenderId() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Sender ID is required"));
        }
        if (dto.getReceiverId() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Receiver ID is required"));
        }
        if (dto.getSubject() == null || dto.getSubject().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Subject is required"));
        }

        return userRepository.findById(dto.getSenderId())
                .chain(sender -> {
                    if (sender == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Sender not found"));
                    }

                    return userRepository.findById(dto.getReceiverId())
                            .chain(receiver -> {
                                if (receiver == null) {
                                    return Uni.createFrom().failure(
                                            new IllegalArgumentException("Receiver not found"));
                                }

                                // Create session entity from DTO
                                Session session = new Session(
                                        sender,
                                        receiver,
                                        dto.getSubject(),
                                        LocalDateTime.now()
                                );

                                return sessionRepository.persist(session);
                            });
                });
    }

    /**
     * Get sessions between two specific users
     */
    public Uni<List<Session>> getSessionsBetweenUsers(UUID userId1, UUID userId2) {
        return userRepository.findById(userId1)
                .chain(user1 -> {
                    if (user1 == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("User 1 not found"));
                    }

                    return userRepository.findById(userId2)
                            .chain(user2 -> {
                                if (user2 == null) {
                                    return Uni.createFrom().failure(
                                            new IllegalArgumentException("User 2 not found"));
                                }

                                return sessionRepository.findSessionsBetweenUsers(userId1, userId2);
                            });
                });
    }

    /**
     * Search sessions by subject
     */
    public Uni<List<Session>> searchSessionsBySubject(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Search term cannot be empty"));
        }
        return sessionRepository.searchBySubject(searchTerm);
    }

    /**
     * Delete a session (cascades to messages)
     */
    public Uni<Boolean> deleteSession(UUID sessionId) {
        return sessionRepository.deleteById(sessionId);
    }

    /**
     * Count sessions for a user
     */
    public Uni<Long> countUserSessions(UUID userId) {
        return sessionRepository.countUserSessions(userId);
    }
}
