package pds.comasy.patterns.placeStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.comasy.dto.PlaceDTO;
import pds.comasy.entity.Place;
import pds.comasy.entity.Republic;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.repository.RepublicRepository;

import java.util.List;

@Service
public class RepublicStrategy implements PlaceStrategy {

    @Autowired
    private RepublicRepository republicRepository;

    @Override
    public Place createPlaceEntity() {
        return new Republic();
    }

    @Override
    public void mapPlaceDtoToEntity(PlaceDTO placeDto, Place place) {
        Republic republic = (Republic) place;
        mapCommonFields(placeDto, republic);
        republic.setQtyRooms(placeDto.getQtyRooms());
        republic.setRentPrice(placeDto.getRentPrice());
    }

    @Override
    public void savePlace(Place place) {
        republicRepository.save((Republic) place);
    }

    @Override
    public void validateFields(PlaceDTO placeDto) throws InvalidFieldException {
        if (placeDto.getQtyRooms() <= 0) {
            throw new InvalidFieldException("Quantidade de Quartos da República deve ser maior que zero.");
        }
        if (placeDto.getRentPrice() <= 0) {
            throw new InvalidFieldException("Valor do aluguel deve ser maior que zero.");
        }
    }

    private void mapCommonFields(PlaceDTO placeDto, Place place) {
        place.setName(placeDto.getName());
        place.setNumber(placeDto.getNumber());
        place.setTelephoneNumber(placeDto.getTelephoneNumber());
        place.setZipcode(placeDto.getZipcode());
        place.setNeighborhood(placeDto.getNeighborhood());
        place.setStreetAddress(placeDto.getStreetAddress());
        place.setCity(placeDto.getCity());
        place.setState(placeDto.getState());
    }

    @Override
    public List<Republic> getAllPlaces() {
        return republicRepository.findAll();
    }
}
