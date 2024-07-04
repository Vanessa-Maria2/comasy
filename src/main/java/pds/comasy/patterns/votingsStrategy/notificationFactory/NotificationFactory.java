package pds.comasy.patterns.votingsStrategy.notificationFactory;

public class NotificationFactory {

    public Notification createNotification(String establishmentType) {
        switch (establishmentType) {
            case "hostel":
                return new HostelNotification();
            case "republic":
                return new RepublicNotification();
            case "condominium":
                return new CondominiumNotification();
            default:
                throw new IllegalArgumentException("Invalid establishment type: " + establishmentType);
        }
    }

}
