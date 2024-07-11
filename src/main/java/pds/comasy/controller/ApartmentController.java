package pds.comasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pds.comasy.dto.ApartmentDto;
import pds.comasy.exceptions.EntityAlreadyExistsException;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.exceptions.NotFoundException;
import pds.comasy.service.ApartmentService;
import pds.comasy.service.CondominiumService;

import java.util.List;

@RestController
@RequestMapping("/apartment")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private CondominiumService condominiumService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("apartment/form");
        modelAndView.addObject("apartment", new ApartmentDto());
        modelAndView.addObject("listCondominium", condominiumService.listCondominium());
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView listApartment(Model model) {
        List<ApartmentDto> apartmentDtoList = apartmentService.listApartment();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(model.addAttribute("list", apartmentDtoList));
        modelAndView.setViewName("apartment/list");
        return modelAndView;
    }

    @PostMapping("/{condominiumId}")
    public ResponseEntity<ApartmentDto> createdApartment(@RequestBody ApartmentDto apartmentDto, @PathVariable Long condominiumId) throws EntityAlreadyExistsException, InvalidFieldException {
        ApartmentDto createdApartmentDto = apartmentService.createdApartment(apartmentDto, condominiumId);
        return new ResponseEntity<>(createdApartmentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDto> updateApartment(@PathVariable Long id, @RequestBody ApartmentDto apartmentDto) throws Exception {
        ApartmentDto updateApartment = apartmentService.updateApartment(id, apartmentDto);
        return ResponseEntity.ok(updateApartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApartment(@PathVariable Long id) {
       try {
           apartmentService.deleteApartment(id);
           return ResponseEntity.noContent().build();
       } catch (NotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }
}
