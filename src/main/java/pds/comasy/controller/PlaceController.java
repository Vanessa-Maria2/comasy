package pds.comasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pds.comasy.dto.PlaceDTO;
import pds.comasy.entity.Place;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @Value("${systemType}")
    private String systemType;

    @Autowired
    private PlaceService placeService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("place/form");
        modelAndView.addObject("place", new PlaceDTO());
        modelAndView.addObject("systemType", systemType);
        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public ModelAndView createPlace(@ModelAttribute("place") PlaceDTO placeDto) {
        ModelAndView modelAndView = new ModelAndView("redirect:/place/list");
        try {
            placeService.savePlace(placeDto);
            modelAndView.addObject("message", "Lugar cadastrado com sucesso!");
            modelAndView.addObject("type", "success");
        } catch (InvalidFieldException e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:/place/form");
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("type", "danger");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:/place/form");
            modelAndView.addObject("message", "Erro ao cadastrar lugar.");
            modelAndView.addObject("type", "danger");
        }
        modelAndView.addObject("systemType", systemType);
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView listPlaces() {
        ModelAndView modelAndView = new ModelAndView("place/list");
        modelAndView.addObject("systemType", systemType);
        List<? extends Place> places;
        switch (systemType) {
            case "condominium":
                places = placeService.getAllCondominiums();
                break;
            case "hostel":
                places = placeService.getAllHostels();
                break;
            case "republic":
                places = placeService.getAllRepublics();
                break;
            default:
                places = null;
                modelAndView.addObject("msg", "Tipo de sistema inválido");
                break;
        }
        modelAndView.addObject("places", places);
        return modelAndView;
    }
    // Add methods for listing, updating, and deleting places
}
