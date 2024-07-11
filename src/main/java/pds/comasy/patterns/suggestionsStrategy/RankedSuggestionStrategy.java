package pds.comasy.patterns.suggestionsStrategy;

import pds.comasy.entity.Suggestion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankedSuggestionStrategy implements SuggestionStrategy {
    @Override
    public List<Suggestion> getSuggestions(List<Suggestion> suggestions) {
        return suggestions.stream()
                .sorted(Comparator.comparing(Suggestion::getQtdVotos).reversed())
                .collect(Collectors.toList());
    }
}
