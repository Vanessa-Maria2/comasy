package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.util.Date;

public class RepublicMessage implements Message {
    Date entryDate, exitDate;

    private double calculateStatus;

    @Override
    public String messageEntrance(Date entryTime) {
        return "Seu contrato iniciou em "+ entryTime.toString();
    }

    @Override
    public String messageExit(Date entryTime, Date exitTime) {
        this.calculateStatus = calculateStatus(entryTime, exitTime);
        return "O seu contrato acaba em " + exitTime + " faltam exatemente " + this.calculateStatus + " dias";
    }

    @Override
    public void setEntryAndExitDate(Date entryDate, Date exitDate) {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        this.calculateStatus = exitTime.getTime() - entryTime.getTime();
        return calculateStatus/ (1000.0 * 60 * 60);
    }
}
