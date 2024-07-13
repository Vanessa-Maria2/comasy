package pds.comasy.patterns.placeStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.comasy.dto.PlaceDTO;
import pds.comasy.entity.Condominium;
import pds.comasy.entity.Place;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.repository.CondominiumRepository;

import java.util.List;

@Service
public class CondominiumStrategy implements PlaceStrategy {

    @Autowired
    private CondominiumRepository condominiumRepository;

    @Override
    public Place createPlaceEntity() {
        return new Condominium();
    }

    @Override
    public void mapPlaceDtoToEntity(PlaceDTO placeDto, Place place) {
        Condominium condominium = (Condominium) place;
        mapCommonFields(placeDto, condominium);
        condominium.setCnpj(placeDto.getCnpj());
        condominium.setQtyBlocks(placeDto.getQtyBlocks());
        condominium.setQtyApartments(placeDto.getQtyApartments());
    }

    @Override
    public void savePlace(Place place) {
        condominiumRepository.save((Condominium) place);
    }

    @Override
    public void validateFields(PlaceDTO placeDto) throws InvalidFieldException {
        if (placeDto.getCnpj() == null || placeDto.getCnpj().isEmpty()) {
            throw new InvalidFieldException("CNPJ é obrigatório.");
        }
        if (placeDto.getQtyBlocks() <= 0) {
            throw new InvalidFieldException("Quantidade de Blocos deve ser maior que zero.");
        }
        if (placeDto.getQtyApartments() <= 0) {
            throw new InvalidFieldException("Quantidade de Apartamentos deve ser maior que zero.");
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
    public List<Condominium> getAllPlaces() {
        return condominiumRepository.findAll();
    }
}
