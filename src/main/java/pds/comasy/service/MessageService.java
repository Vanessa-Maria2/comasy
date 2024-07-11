package pds.comasy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pds.comasy.entity.UserAuthentication;
import pds.comasy.patterns.votingsStrategy.messageFactory.MessageFactory;

import java.util.List;

@Service
public class MessageService {

    @Value("${systemType}")
    private String systemType;

    public List<String> getMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthentication user = (UserAuthentication) authentication.getPrincipal();

        MessageFactory messageFactory = new MessageFactory();

        List<String> msgs = messageFactory.createMessage(systemType, user.getEntryDate(), user.getExitDate());
        return msgs;
    }
}
