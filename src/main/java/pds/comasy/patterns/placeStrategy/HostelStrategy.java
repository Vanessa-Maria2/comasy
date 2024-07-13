package pds.comasy.patterns.placeStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pds.comasy.dto.PlaceDTO;
import pds.comasy.entity.Hostel;
import pds.comasy.entity.Place;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.repository.HostelRepository;

import java.util.List;

@Service
public class HostelStrategy implements PlaceStrategy {

    @Autowired
    private HostelRepository hostelRepository;

    @Override
    public Place createPlaceEntity() {
        return new Hostel();
    }

    @Override
    public void mapPlaceDtoToEntity(PlaceDTO placeDto, Place place) {
        Hostel hostel = (Hostel) place;
        mapCommonFields(placeDto, hostel);
        hostel.setCnpj(placeDto.getCnpj());
        hostel.setCapacity(placeDto.getCapacity());
        hostel.setQtyRooms(placeDto.getQtyRooms());
        hostel.setResidentOwnerCpf(placeDto.getResidentOwnerCpf());
    }

    @Override
    public void savePlace(Place place) {
        hostelRepository.save((Hostel) place);
    }

    @Override
    public void validateFields(PlaceDTO placeDto) throws InvalidFieldException {
        if (placeDto.getCnpj() == null || placeDto.getCnpj().isEmpty()) {
            throw new InvalidFieldException("CNPJ é obrigatório.");
        }
        if (placeDto.getResidentOwnerCpf() == null || placeDto.getResidentOwnerCpf().isEmpty()) {
            throw new InvalidFieldException("CPF do Proprietário é obrigatório.");
        }
        if (placeDto.getCapacity() <= 0) {
            throw new InvalidFieldException("Capacidade do Hostel deve ser maior que zero.");
        }
        if (placeDto.getQtyRooms() <= 0) {
            throw new InvalidFieldException("Quantidade de Quartos do Hostel deve ser maior que zero.");
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
    public List<Hostel> getAllPlaces() {
        return hostelRepository.findAll();
    }
}
