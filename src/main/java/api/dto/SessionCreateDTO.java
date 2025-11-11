package api.dto;

import java.util.UUID;

public class SessionCreateDTO {
    public UUID senderId;
    public UUID receiverId;
    public String subject;

    public SessionCreateDTO() {}

    public SessionCreateDTO(UUID senderId, UUID receiverId, String subject) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.subject = subject;
    }

    public UUID getSenderId() { return senderId; }
    public void setSenderId(UUID senderId) { this.senderId = senderId; }

    public UUID getReceiverId() { return receiverId; }
    public void setReceiverId(UUID receiverId) { this.receiverId = receiverId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
