package api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionDTO {

    public UUID sessionId;
    public String subject;
    public LocalDateTime creationDate;
    public String senderName;
    public String receiverName;
    public int messageCount;

    public SessionDTO(UUID sessionId, String subject, LocalDateTime creationDate,
                      String senderName, String receiverName, int messageCount) {
        this.sessionId = sessionId;
        this.subject = subject;
        this.creationDate = creationDate;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.messageCount = messageCount;
    }
}
