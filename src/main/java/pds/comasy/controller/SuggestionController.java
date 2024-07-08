package pds.comasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pds.comasy.entity.Suggestion;
import pds.comasy.exceptions.EntitySaveFailureException;
import pds.comasy.exceptions.FailedToDeleteException;
import pds.comasy.exceptions.NotFoundException;
import pds.comasy.service.SuggestionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @Value("${systemType}")
    private String systemType;

    @PostMapping
    public ResponseEntity<?> createSuggestion(@RequestBody Suggestion suggestion) {
        try {
            Suggestion createdSuggestion = suggestionService.createSuggestion(suggestion);
            return ResponseEntity.ok(createdSuggestion);
        } catch (EntitySaveFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "An internal server error occurred."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> getSuggestionById(@PathVariable Long id) {
        Optional<Suggestion> suggestion = suggestionService.getSuggestionById(id);
        return suggestion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public ModelAndView getSuggestions() {
        List<Suggestion> suggestions = suggestionService.getSuggestions();
        ModelAndView model = new ModelAndView();
        model.setViewName("suggestion/list");
        model.addObject("suggestions", suggestions);
        model.addObject("isCondominium", "condominium".equals(systemType));
        model.addObject("isHostel", "hostel".equals(systemType));
        model.addObject("isRepublic", "republic".equals(systemType));
        return model;
    }

    @PutMapping("/{id}/vote")
    public ResponseEntity<?> updateSuggestionVotes(@PathVariable Long id) {
        try {
            Suggestion existingSuggestion = suggestionService.getSuggestionById(id).orElse(null);
            if (existingSuggestion != null) {
                existingSuggestion.setQtdVotos(existingSuggestion.getQtdVotos() + 1);
                Suggestion updatedSuggestion = suggestionService.updateSuggestion(id, existingSuggestion);
                return ResponseEntity.ok(updatedSuggestion);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Suggestion not found."));
            }
        } catch (EntitySaveFailureException | NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSuggestion(@PathVariable Long id) {
        try {
            suggestionService.deleteSuggestion(id);
            return ResponseEntity.noContent().build();
        } catch (FailedToDeleteException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
