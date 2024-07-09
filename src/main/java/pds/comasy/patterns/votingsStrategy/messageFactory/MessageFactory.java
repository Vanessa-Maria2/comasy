package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.util.Date;
import java.util.HashMap;

public class MessageFactory {
    HashMap<String, Message> messagesOfEstablisment;

    public MessageFactory() {
        messagesOfEstablisment = new HashMap<>();
        messagesOfEstablisment.put("hostel", new HostelMessage());
        messagesOfEstablisment.put("republic", new RepublicMessage());
        messagesOfEstablisment.put("condominium", new CondominiumMessage());
    }

    public Message createMessage(String establishmentType, Date entryDate, Date exitDate) {
        Message message = this.messagesOfEstablisment.get(establishmentType);
        if (message == null) {
            throw new IllegalArgumentException("Invalid establishment type: " + establishmentType);
        }
        message.setEntryAndExitDate(entryDate, exitDate);

        return message;
    }
}
