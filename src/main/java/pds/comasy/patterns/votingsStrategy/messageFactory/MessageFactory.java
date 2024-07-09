package pds.comasy.patterns.votingsStrategy.messageFactory;

public class MessageFactory {

    public Message createNotification(String establishmentType) {
        switch (establishmentType) {
            case "hostel":
                return new HostelMessage();
            case "republic":
                return new RepublicMessage();
            case "condominium":
                return new CondominiumMessage();
            default:
                throw new IllegalArgumentException("Invalid establishment type: " + establishmentType);
        }
    }

}
