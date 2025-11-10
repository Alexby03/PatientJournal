package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class Session extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sessionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverId;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @OneToMany (
        mappedBy = "session",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Message> messages = new ArrayList<>();

    public Session() {}

    public Session(User senderId, User receiverId, String subject, LocalDateTime creationDate) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.subject = subject;
        this.creationDate = creationDate;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public User getSenderId() {
        return senderId;
    }

    public User getReceiverId() {
        return receiverId;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
        message.setSession(this);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
        message.setSession(null);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", subject='" + subject + '\'' +
                ", creationDate=" + creationDate +
                ", messages=" + messages +
                '}';
    }
}
