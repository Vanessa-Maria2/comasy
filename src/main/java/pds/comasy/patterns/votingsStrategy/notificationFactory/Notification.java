package pds.comasy.patterns.votingsStrategy.notificationFactory;

import java.util.Date;

public interface Notification {
    String notifyEntrance(Date entryTime);

    String notifyExit(Date entryTime, Date exitTime);

    double calculateStatus(Date entryTime, Date exitTime);
}
