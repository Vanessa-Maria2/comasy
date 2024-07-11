package pds.comasy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pds.comasy.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public ModelAndView getMessageList() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        try {
            List<String> messageList = messageService.getMessage();
            model.addObject("messages", messageList);
            return model;
        } catch (Exception e) {
            return model;
        }
    }
}
