package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RepublicMessage implements Message {
    Date entryDate, exitDate;

    private double calculateStatus;

    @Override
    public String messageEntrance(Date entryTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "O seu contrato iniciou em "+ dateFormat.format(entryTime);
    }

    @Override
    public String messageExit(Date entryTime, Date exitTime) {
        this.calculateStatus = calculateStatus(entryTime, exitTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "O seu contrato acaba em " + dateFormat.format(exitTime) + " faltam exatemente " + this.calculateStatus + " dias";
    }

    @Override
    public void setEntryAndExitDate(Date entryDate, Date exitDate) {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        Date dateCurrent = new Date();
        this.calculateStatus = exitTime.getTime() - dateCurrent.getTime();
        return calculateStatus/ (1000 * 60 * 60 * 24);
    }
}
