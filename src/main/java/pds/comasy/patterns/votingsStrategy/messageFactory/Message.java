package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.util.Date;

public interface Message {
    String notifyEntrance(Date entryTime);

    String notifyExit(Date entryTime, Date exitTime);

    double calculateStatus(Date entryTime, Date exitTime);
}
