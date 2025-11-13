package data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private UUID messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    @JsonIgnore
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonIgnore
    private User sender;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    public Message() {
    }

    public Message(Session session, User sender, String messageContent) {
        this.session = session;
        this.sender = sender;
        this.message = messageContent;
        this.dateTime = LocalDateTime.now();
    }

    public UUID getMessageId() {
        return messageId;
    }

    public Session getSessionId() {
        return session;
    }

    public User getSenderId() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", session=" + session +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
