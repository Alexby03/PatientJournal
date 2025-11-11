package api.dto;

import java.util.UUID;

public class MessageCreateDTO {
    public UUID sessionId;
    public UUID senderId;
    public String message;

    public MessageCreateDTO() {}

    public MessageCreateDTO(UUID sessionId, UUID senderId, String message) {
        this.sessionId = sessionId;
        this.senderId = senderId;
        this.message = message;
    }

    public UUID getSessionId() { return sessionId; }
    public void setSessionId(UUID sessionId) { this.sessionId = sessionId; }

    public UUID getSenderId() { return senderId; }
    public void setSenderId(UUID senderId) { this.senderId = senderId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
