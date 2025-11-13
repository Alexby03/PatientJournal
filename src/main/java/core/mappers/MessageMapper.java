package core.mappers;

import api.dto.MessageDTO;
import data.entities.Message;

public class MessageMapper {

    public static MessageDTO toDTO(Message message) {
        if (message == null) return null;

        return new MessageDTO(
                message.getMessageId(),
                message.getSessionId() != null ? message.getSessionId().getSessionId() : null,
                message.getSenderId() != null ? message.getSenderId().getId() : null,
                message.getMessage(),
                message.getDateTime()
        );
    }
}
