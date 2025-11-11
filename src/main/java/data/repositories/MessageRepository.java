package data.repositories;

import data.entities.Message;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MessageRepository implements PanacheRepository<Message> {

    /**
     * Find messages by session ID
     */
    public Uni<List<Message>> findBySessionId(UUID sessionId) {
        return find("session.sessionId", sessionId).list();
    }

    /**
     * Find messages sent by a specific user
     */
    public Uni<List<Message>> findBySenderId(UUID senderId) {
        return find("sender.id", senderId).list();
    }

    /**
     * Search messages by content
     */
    public Uni<List<Message>> searchByMessageContent(String contentPattern) {
        return find("message like ?1", "%" + contentPattern + "%").list();
    }

    /**
     * Get messages in a session with pagination
     */
    public Uni<List<Message>> findSessionMessagesWithPagination(UUID sessionId, int pageIndex, int pageSize) {
        return find("session.sessionId", sessionId)
                .page(pageIndex, pageSize)
                .list();
    }

    /**
     * Count messages in a session
     */
    public Uni<Long> countBySession(UUID sessionId) {
        return count("session.sessionId", sessionId);
    }

    /**
     * Delete messages older than a specific date
     */
    public Uni<Boolean> deleteOlderThan(LocalDateTime cutoffDate) {
        return delete("dateTime < ?1", cutoffDate).map(count -> count > 0);
    }

    /**
     * Get the latest message in a session
     */
    public Uni<Message> findLatestMessageInSession(UUID sessionId) {
        return find("session.sessionId = ?1 order by dateTime desc", sessionId)
                .firstResult();
    }
}
