package api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDTO {

    public UUID messageId;
    public UUID sessionId;
    public UUID senderId;
    public String message;
    public LocalDateTime dateTime;

    public MessageDTO(UUID messageId, UUID sessionId, UUID senderId, String message, LocalDateTime dateTime) {
        this.messageId = messageId;
        this.sessionId = sessionId;
        this.senderId = senderId;
        this.message = message;
        this.dateTime = dateTime;
    }
}
