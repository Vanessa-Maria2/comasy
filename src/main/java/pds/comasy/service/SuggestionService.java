package pds.comasy.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pds.comasy.entity.Resident;
import pds.comasy.entity.Suggestion;
import pds.comasy.exceptions.EntitySaveFailureException;
import pds.comasy.exceptions.FailedToDeleteException;
import pds.comasy.exceptions.NotFoundException;
import pds.comasy.patterns.votingsStrategy.CategorizedSuggestionStrategy;
import pds.comasy.patterns.votingsStrategy.RankedSuggestionStrategy;
import pds.comasy.patterns.votingsStrategy.StandardSuggestionStrategy;
import pds.comasy.patterns.votingsStrategy.SuggestionStrategy;
import pds.comasy.repository.ResidentRepository;
import pds.comasy.repository.SuggestionRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final ResidentRepository residentRepository;

    private final Map<String, SuggestionStrategy> strategyMap = new HashMap<>();

    @Value("${systemType}")
    private String systemType;

    public SuggestionService(SuggestionRepository suggestionRepository, ResidentRepository residentRepository) throws IOException, ParseException, EntitySaveFailureException, CsvException {
        this.suggestionRepository = suggestionRepository;
        this.residentRepository = residentRepository;
        initializeStrategyMap();
        String filePath = "static/csv/ocorrencias.csv";
        processCsv(filePath);
    }

    private void initializeStrategyMap() {
        strategyMap.put("condominium", new RankedSuggestionStrategy());
        strategyMap.put("hostel", new CategorizedSuggestionStrategy());
        strategyMap.put("republic", new StandardSuggestionStrategy());
    }

    public Suggestion createSuggestion(Suggestion suggestion) throws EntitySaveFailureException {
        try {
            return suggestionRepository.save(suggestion);
        } catch (Exception e) {
            throw new EntitySaveFailureException("Failed to save suggestion");
        }
    }

    public void processCsv(String filePath) throws IOException, CsvException, ParseException, EntitySaveFailureException {
        ClassPathResource resource = new ClassPathResource(filePath);
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            csvReader.skip(1);
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                String residentIdStr = record[1];
                String horaStr = record[2];
                String mensagem = truncate(record[3]);

                Long residentId = Long.parseLong(residentIdStr);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date dataProposta = formatter.parse(horaStr);

                Resident resident = residentRepository.findById(residentId).orElse(null);

                Suggestion existingSuggestion = suggestionRepository.findByTypeAndMessageAndDataPropostaAndResident(
                        "Sugestão", mensagem, dataProposta
                );

                if (existingSuggestion == null) {
                    Suggestion suggestion = new Suggestion();
                    suggestion.setType("Sugestão");
                    suggestion.setMessage(truncate(mensagem));
                    suggestion.setQtdVotos(0);
                    suggestion.setDataProposta(dataProposta);
                    suggestion.setResident(resident);
                    suggestion.setActive(true);

                    suggestionRepository.save(suggestion);
                }
            }
        } catch (IOException | CsvException | ParseException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntitySaveFailureException("Failed to process CSV");
        }
    }

    private String truncate(String text) {
        if (text == null || text.length() <= 255) {
            return text;
        } else {
            return text.substring(0, 255);
        }
    }

    public Optional<Suggestion> getSuggestionById(Long id) {
        return suggestionRepository.findById(id);
    }

    public List<Suggestion> getSuggestions() {
        List<Suggestion> suggestions = suggestionRepository.findByActiveTrue();
        SuggestionStrategy strategy = strategyMap.getOrDefault(systemType, new StandardSuggestionStrategy());
        return strategy.getSuggestions(suggestions);
    }

    public Suggestion updateSuggestion(Long id, Suggestion suggestion) throws EntitySaveFailureException, NotFoundException {
        if (suggestionRepository.existsById(id)) {
            suggestion.setId(id);
            try {
                return suggestionRepository.save(suggestion);
            } catch (Exception e) {
                throw new EntitySaveFailureException("Failed to update suggestion");
            }
        } else {
            throw new NotFoundException("Suggestion not found");
        }
    }

    public void deleteSuggestion(Long id) throws FailedToDeleteException, NotFoundException {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suggestion not found"));

        if (suggestion.getActive()) {
            suggestion.setActive(false);
            suggestionRepository.save(suggestion);
        } else {
            throw new FailedToDeleteException("Suggestion is already inactive");
        }
    }
}
