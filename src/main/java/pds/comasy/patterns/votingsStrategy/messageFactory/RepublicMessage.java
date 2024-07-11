package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RepublicMessage implements Message {

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
        return "O seu contrato acaba no dia " + dateFormat.format(exitTime) + " faltam " + this.calculateStatus + " horas";
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        Date dateCurrent = new Date();
        this.calculateStatus = exitTime.getTime() - dateCurrent.getTime();
        return Math.round(calculateStatus/(1000 * 60 * 60));
    }
}
