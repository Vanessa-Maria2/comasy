package pds.comasy.patterns.votingsStrategy.messageFactory;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class HostelMessage implements Message {

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
    public double calculateStatus(Date entryF, Date exitTime) {
        Date currentDate = new Date();
        this.calculateStatus = exitTime.getTime() - currentDate.getTime();
        return Math.round(calculateStatus/ (1000 * 60 * 60 * 24));
    }
}
