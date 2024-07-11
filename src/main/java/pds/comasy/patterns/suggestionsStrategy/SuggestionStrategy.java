package pds.comasy.patterns.suggestionsStrategy;

import pds.comasy.entity.Suggestion;

import java.util.List;

public interface SuggestionStrategy {
    List<Suggestion> getSuggestions(List<Suggestion> suggestions);
}

