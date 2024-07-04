package pds.comasy.patterns.votingsStrategy.notificationFactory;

import java.util.Date;

public class HostelNotification implements Notification {

    @Override
    public String notifyEntrance(Date entryTime) {
        return "Você tem visita agendado para hoje dia " + entryTime.getTime();
    }

    @Override
    public String notifyExit(Date entryTime, Date exitTime) {
        return "Fim da visita: " + exitTime.getTime();
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        return 0;
    }
}
