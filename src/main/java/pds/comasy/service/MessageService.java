package pds.comasy.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pds.comasy.entity.UserAuthentication;
import pds.comasy.patterns.votingsStrategy.messageFactory.MessageFactory;

@Service
public class MessageService {

    public MessageFactory getMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthentication user = (UserAuthentication) authentication.getPrincipal();
        MessageFactory messageFactory = new MessageFactory();
        messageFactory.createMessage("hostel", user.getEntryDate(), user.getExitDate()).messageExit(user.getEntryDate(), user.getExitDate());
        return messageFactory;
    }
}
