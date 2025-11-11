package data.repositories;

import data.entities.Session;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SessionRepository implements PanacheRepository<Session> {

    /**
     * Find sessions by sender ID
     */
    public Uni<List<Session>> findBySenderId(UUID senderId) {
        return find("senderId.id", senderId).list();
    }

    /**
     * Find sessions by receiver ID
     */
    public Uni<List<Session>> findByReceiverId(UUID receiverId) {
        return find("receiverId.id", receiverId).list();
    }

    /**
     * Find sessions between two users
     */
    public Uni<List<Session>> findSessionsBetweenUsers(UUID userId1, UUID userId2) {
        return find("(senderId.id = ?1 and receiverId.id = ?2) or (senderId.id = ?2 and receiverId.id = ?1)",
                userId1, userId2).list();
    }

    /**
     * Search sessions by subject
     */
    public Uni<List<Session>> searchBySubject(String subjectPattern) {
        return find("subject like ?1", "%" + subjectPattern + "%").list();
    }

    /**
     * Get all sessions for a user (sent or received)
     */
    public Uni<List<Session>> findAllUserSessions(UUID userId) {
        return find("senderId.id = ?1 or receiverId.id = ?1", userId).list();
    }

    /**
     * Count sessions involving a user
     */
    public Uni<Long> countUserSessions(UUID userId) {
        return count("senderId.id = ?1 or receiverId.id = ?1", userId);
    }

    /**
     * Get sessions with pagination
     */
    public Uni<List<Session>> findSessionsWithPagination(UUID userId, int pageIndex, int pageSize) {
        return find("senderId.id = ?1 or receiverId.id = ?1", userId)
                .page(pageIndex, pageSize)
                .list();
    }
}
