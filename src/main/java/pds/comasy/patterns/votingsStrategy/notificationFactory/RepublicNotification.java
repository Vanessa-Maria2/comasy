package pds.comasy.patterns.votingsStrategy.notificationFactory;

import lombok.Data;

import java.util.Date;

@Data
public class RepublicNotification implements Notification {
    private Date entryTime;
    private Date exitTime;
    private double calculateStatus;

    @Override
    public String notifyEntrance(Date entryTime) {
        return "Seu contrato iniciou em "+ entryTime.toString();
    }

    @Override
    public String notifyExit(Date entryTime, Date exitTime) {
        this.calculateStatus = calculateStatus(entryTime, exitTime);
        return "O seu contrato acaba em " + exitTime + " faltam exatemente " + this.calculateStatus + " dias";
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        this.calculateStatus = getExitTime().getTime() - getEntryTime().getTime();
        return calculateStatus/ (1000.0 * 60 * 60);
    }
}
