package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.util.Date;

public class RepublicMessage implements Message {
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
        this.calculateStatus = exitTime.getTime() - entryTime.getTime();
        return calculateStatus/ (1000.0 * 60 * 60);
    }
}
