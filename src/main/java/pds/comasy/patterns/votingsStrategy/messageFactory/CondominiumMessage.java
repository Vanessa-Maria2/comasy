package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CondominiumMessage implements Message {

    @Override
    public String messageEntrance(Date entryTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "O seu contrato iniciou em "+ dateFormat.format(entryTime);
    }

    @Override
    public String messageExit(Date entryTime, Date exitTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "O seu contrato acaba em " + dateFormat.format(exitTime);
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        return 0;
    }
}
