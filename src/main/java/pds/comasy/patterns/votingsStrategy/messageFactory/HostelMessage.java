package pds.comasy.patterns.votingsStrategy.messageFactory;

import java.util.Date;

public class HostelMessage implements Message {

    @Override
    public String messageEntrance(Date entryTime) {
        return "Você tem visita agendado para hoje dia " + entryTime.getTime();
    }

    @Override
    public String messageExit(Date entryTime, Date exitTime) {
        return "Fim da visita: " + exitTime.getTime();
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        return 0;
    }
}
