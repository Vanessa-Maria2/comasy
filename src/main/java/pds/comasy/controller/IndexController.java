package pds.comasy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class IndexController {

    @Value("${systemType}")
    private String systemType;

    @GetMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView model = new ModelAndView("login");
        model.addObject("systemType", systemType);
        return model;
    }

    @GetMapping(value = "/logout")
    public ModelAndView logout() {
        return new ModelAndView("login");
    }
}
