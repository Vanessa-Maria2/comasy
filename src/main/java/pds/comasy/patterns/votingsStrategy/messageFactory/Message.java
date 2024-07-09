package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.util.Date;

public interface Message {
    String messageEntrance(Date entryTime);

    String messageExit(Date entryTime, Date exitTime);

    void setEntryAndExitDate(Date entryDate, Date exitDate);

    double calculateStatus(Date entryTime, Date exitTime);
}
