package core.services;

import data.entities.Message;
import data.entities.Session;
import data.entities.User;
import data.repositories.MessageRepository;
import data.repositories.SessionRepository;
import data.repositories.UserRepository;
import api.dto.MessageCreateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MessageService {

    @Inject
    MessageRepository messageRepository;

    @Inject
    SessionRepository sessionRepository;

    @Inject
    UserRepository userRepository;

    /**
     * Get all messages in a session
     */
    public Uni<List<Message>> getSessionMessages(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .chain(session -> {
                    if (session == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Session not found"));
                    }
                    return messageRepository.findBySessionId(sessionId);
                });
    }

    /**
     * Get message by ID
     */
    public Uni<Message> getMessageById(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    /**
     * Create a new message from DTO
     */
    public Uni<Message> createMessage(MessageCreateDTO dto) {
        if (dto.getSessionId() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Session ID is required"));
        }
        if (dto.getSenderId() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Sender ID is required"));
        }
        if (dto.getMessage() == null || dto.getMessage().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Message content is required"));
        }

        return sessionRepository.findById(dto.getSessionId())
                .chain(session -> {
                    if (session == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Session not found"));
                    }

                    return userRepository.findById(dto.getSenderId())
                            .chain(sender -> {
                                if (sender == null) {
                                    return Uni.createFrom().failure(
                                            new IllegalArgumentException("Sender not found"));
                                }

                                Message message = new Message(
                                        session,
                                        sender,
                                        dto.getMessage()
                                );

                                return messageRepository.persist(message);
                            });
                });
    }

    /**
     * Delete a message
     */
    public Uni<Boolean> deleteMessage(UUID messageId) {
        return messageRepository.deleteById(messageId);
    }

    /**
     * Search messages by content
     */
    public Uni<List<Message>> searchMessages(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Search term cannot be empty"));
        }
        return messageRepository.searchByMessageContent(searchTerm);
    }

    /**
     * Get latest message in a session
     */
    public Uni<Message> getLatestMessage(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .chain(session -> {
                    if (session == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Session not found"));
                    }
                    return messageRepository.findLatestMessageInSession(sessionId);
                });
    }

    /**
     * Count messages in a session
     */
    public Uni<Long> countSessionMessages(UUID sessionId) {
        return messageRepository.countBySession(sessionId);
    }
}
