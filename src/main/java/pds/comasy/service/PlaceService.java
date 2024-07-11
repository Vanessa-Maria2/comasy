package pds.comasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pds.comasy.dto.ApartmentDto;
import pds.comasy.dto.PlaceDTO;
import pds.comasy.entity.Apartment;
import pds.comasy.entity.Condominium;
import pds.comasy.entity.Hostel;
import pds.comasy.entity.Place;
import pds.comasy.entity.Republic;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.repository.ApartmentRepository;
import pds.comasy.repository.CondominiumRepository;
import pds.comasy.repository.HostelRepository;
import pds.comasy.repository.RepublicRepository;

import java.util.List;

@Service
@Transactional
public class PlaceService {

    @Autowired
    private CondominiumRepository condominiumRepository;

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private RepublicRepository republicRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Value("${systemType}")
    private String systemType;

    public List<Condominium> getAllCondominiums() {
        return condominiumRepository.findAll();
    }

    public List<Hostel> getAllHostels() {
        return hostelRepository.findAll();
    }

    public List<Republic> getAllRepublics() {
        return republicRepository.findAll();
    }

    public void savePlace(PlaceDTO placeDto) throws InvalidFieldException {
        validateFields(placeDto);

        if ("condominium".equals(systemType)) {
            Condominium condominium = new Condominium();
            mapPlaceDtoToCondominium(placeDto, condominium);

            // Save Condominium
            condominiumRepository.save(condominium);

//            // Save apartments if available
//            if (placeDto.getApartmentDtos() != null) {
//                for (ApartmentDto apartmentDto : placeDto.getApartmentDtos()) {
//                    Apartment apartment = new Apartment();
//                    mapApartmentDtoToApartment(apartmentDto, apartment);
//                    apartment.setCondominium(condominium); // Set the relationship
//                    apartmentRepository.save(apartment);
//                }
//            }

        } else if ("hostel".equals(systemType)) {
            Hostel hostel = new Hostel();
            mapPlaceDtoToHostel(placeDto, hostel);

            // Save Hostel
            hostelRepository.save(hostel);

        } else if ("republic".equals(systemType)) {
            Republic republic = new Republic();
            mapPlaceDtoToRepublic(placeDto, republic);

            // Save Republic
            republicRepository.save(republic);

        } else {
            throw new InvalidFieldException("Invalid system type");
        }
    }

    private void mapPlaceDtoToCondominium(PlaceDTO placeDto, Condominium condominium) {
        mapCommonFields(placeDto, condominium); // Map common fields
        condominium.setCnpj(placeDto.getCnpj());
        condominium.setQtyBlocks(placeDto.getQtyBlocks());
        condominium.setQtyApartments(placeDto.getQtyApartments());
    }

    private void mapPlaceDtoToHostel(PlaceDTO placeDto, Hostel hostel) {
        mapCommonFields(placeDto, hostel); // Map common fields
        hostel.setCnpj(placeDto.getCnpj());
        hostel.setCapacity(placeDto.getCapacity());
        hostel.setQtyRooms(placeDto.getQtyRooms());
        hostel.setResidentOwnerCpf(placeDto.getResidentOwnerCpf());
    }

    private void mapPlaceDtoToRepublic(PlaceDTO placeDto, Republic republic) {
        mapCommonFields(placeDto, republic); // Map common fields
        republic.setQtyRooms(placeDto.getQtyRooms());
        republic.setRentPrice(placeDto.getRentPrice());
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

    private void validateFields(PlaceDTO placeDto) throws InvalidFieldException {
        if (placeDto.getName() == null || placeDto.getName().isEmpty()) {
            throw new InvalidFieldException("Nome é obrigatório.");
        }
        if (placeDto.getCity() == null || placeDto.getCity().isEmpty() ||
                placeDto.getState() == null || placeDto.getState().isEmpty() ||
                placeDto.getNeighborhood() == null || placeDto.getNeighborhood().isEmpty() ||
                placeDto.getStreetAddress() == null || placeDto.getStreetAddress().isEmpty() ||
                placeDto.getZipcode() == null || placeDto.getZipcode().isEmpty()) {
            throw new InvalidFieldException("Todos os campos relacionados ao endereço do lugar são obrigatórios.");
        }
        if ("condominium".equals(systemType)) {
            if (placeDto.getCnpj() == null || placeDto.getCnpj().isEmpty()) {
                throw new InvalidFieldException("CNPJ é obrigatório.");
            }
            if (placeDto.getQtyBlocks() <= 0) {
                throw new InvalidFieldException("Quantidade de Blocos deve ser maior que zero.");
            }
            if (placeDto.getQtyApartments() <= 0) {
                throw new InvalidFieldException("Quantidade de Apartamentos deve ser maior que zero.");
            }
        } else if ("hostel".equals(systemType)) {
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
        } else if ("republic".equals(systemType)) {
            if (placeDto.getQtyRooms() <= 0) {
                throw new InvalidFieldException("Quantidade de Quartos da República deve ser maior que zero.");
            }
        }
    }
}