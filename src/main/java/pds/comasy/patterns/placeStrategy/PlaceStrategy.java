package pds.comasy.patterns.placeStrategy;

import pds.comasy.dto.PlaceDTO;
import pds.comasy.entity.Place;
import pds.comasy.exceptions.InvalidFieldException;

import java.util.List;

public interface PlaceStrategy {
    void mapPlaceDtoToEntity(PlaceDTO placeDto, Place place);
    Place createPlaceEntity();
    void savePlace(Place place);
    void validateFields(PlaceDTO placeDto) throws InvalidFieldException;
    List<? extends Place> getAllPlaces();
}