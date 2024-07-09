package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.util.Date;

public class CondominiumMessage implements Message {

    Date entryDate, exitDate;

    double calculateStatus;

    @Override
    public String messageEntrance(Date entryTime) {
        return "A entrega chegou no dia " + entryTime.toString() + " na portaria do condomínio";
    }

    @Override
    public String messageExit(Date entryTime, Date exitTime) {
        this.calculateStatus = calculateStatus(entryTime, exitTime);
        return "A entrega foi retirada no dia " + exitTime.toString() + " após " + calculateStatus + " dias na portaria";
    }

    @Override
    public void setEntryAndExitDate(Date entryDate, Date exitDate) {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        this.calculateStatus = exitTime.getTime() - entryTime.getTime();
        return this.calculateStatus/(1000 * 60 * 60 * 24);
    }
}
