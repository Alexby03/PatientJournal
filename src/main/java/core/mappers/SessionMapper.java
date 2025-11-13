package core.mappers;

import api.dto.SessionDTO;
import data.entities.Session;
import data.entities.User;

public class SessionMapper {

    public static SessionDTO toDTO(Session session) {
        if (session == null) return null;

        User sender = session.getSenderId();
        User receiver = session.getReceiverId();

        String senderName = (sender != null) ? sender.getFullName() : null;
        String receiverName = (receiver != null) ? receiver.getFullName() : null;
        int messageCount = (session.getMessages() != null) ? session.getMessages().size() : 0;

        return new SessionDTO(
                session.getSessionId(),
                session.getSubject(),
                session.getCreationDate(),
                senderName,
                receiverName,
                messageCount
        );
    }
}
