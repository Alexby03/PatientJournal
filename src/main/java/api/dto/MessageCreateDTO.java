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
}
