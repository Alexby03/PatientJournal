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
}
