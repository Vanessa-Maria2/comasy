package pds.comasy.patterns.messageFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MessageFactory {
    HashMap<String, Message> messagesOfEstablisment;

    public MessageFactory() {
        messagesOfEstablisment = new HashMap<>();
        messagesOfEstablisment.put("hostel", new HostelMessage());
        messagesOfEstablisment.put("republic", new RepublicMessage());
        messagesOfEstablisment.put("condominium", new CondominiumMessage());
    }

    public List<String> createMessage(String establishmentType, Date entryDate, Date exitDate) {
        Message message = this.messagesOfEstablisment.get(establishmentType);
        if (message == null) {
            throw new IllegalArgumentException("Invalid establishment type: " + establishmentType);
        }
        String msgEntry = message.messageEntrance(entryDate);
        String msgExit = message.messageExit(entryDate, exitDate);
        List<String> messages = new ArrayList<>();

        messages.add(msgEntry);
        messages.add(msgExit);

        return messages;
    }
}
