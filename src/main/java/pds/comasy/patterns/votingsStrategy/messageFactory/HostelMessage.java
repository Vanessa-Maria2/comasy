package pds.comasy.patterns.votingsStrategy.messageFactory;

import lombok.Data;

import java.util.Date;

@Data
public class HostelMessage implements Message {

    Date entryDate, exitDate;

    @Override
    public String messageEntrance(Date entryTime) {
        return "Você tem visita agendado para hoje dia " + entryTime.getTime();
    }

    @Override
    public String messageExit(Date entryTime, Date exitTime) {
        return "Fim da visita: " + exitTime.getTime();
    }

    @Override
    public void setEntryAndExitDate(Date entryDate, Date exitDate) {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    @Override
    public double calculateStatus(Date entryTime, Date exitTime) {
        return 0;
    }
}
