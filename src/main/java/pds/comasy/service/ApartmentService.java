package pds.comasy.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pds.comasy.dto.ApartmentDto;
import pds.comasy.entity.*;
import pds.comasy.exceptions.EntityAlreadyExistsException;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.exceptions.NotFoundException;
import pds.comasy.mapper.ApartmentMapper;
import pds.comasy.repository.ApartmentRepository;
import pds.comasy.repository.CondominiumRepository;
import pds.comasy.repository.HostelRepository;
import pds.comasy.repository.RepublicRepository;

import java.util.List;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private CondominiumRepository condominiumRepository;

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private RepublicRepository republicRepository;

    @Value("${systemType}")
    private String systemType;

    @Transactional
    public ApartmentDto createdApartment(ApartmentDto apartmentDto, Long placeId) throws EntityAlreadyExistsException, InvalidFieldException {
       Place place = null;

        switch (systemType) {
            case "condominium":
                place = condominiumRepository.findById(placeId).orElseThrow(() ->
                        new EntityNotFoundException("Condominium with id " + placeId + " not found"));
                break;
            case "hostel":
                place = hostelRepository.findById(placeId).orElseThrow(() ->
                        new EntityNotFoundException("Hostel with id " + placeId + " not found"));
                break;
            case "republic":
                place = republicRepository.findById(placeId).orElseThrow(() ->
                        new EntityNotFoundException("Republic with id " + placeId + " not found"));
                break;
            default:
        }

        apartmentDto.setPlace_id(placeId);
        validarCampos(apartmentDto);
        Apartment apartment = ApartmentMapper.mapToApartment(apartmentDto);
        apartmentRepository.save(apartment);

        return ApartmentMapper.mapToApartmentDto(apartment);
    }

    public List<ApartmentDto> listApartment() {
        List<ApartmentDto> apartmentDtoList = ApartmentMapper.mapToListApartmentDto(apartmentRepository.findAll());
        return apartmentDtoList;
    }

    public void deleteApartment(Long id) throws NotFoundException {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Apartment not found"));
        apartmentRepository.deleteById(id);
    }

    public ApartmentDto updateApartment(Long id, ApartmentDto apartmentDto) throws NotFoundException {
        Apartment existingApartment = apartmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Apartment not found"));
        Apartment updateApartment = ApartmentMapper.mapToApartment(apartmentDto);
        updateApartment.setId(existingApartment.getId());

        Apartment savedApartment = apartmentRepository.save(updateApartment);
        return ApartmentMapper.mapToApartmentDto(savedApartment);
    }

    public void validarCampos(ApartmentDto apartmentDto) throws EntityAlreadyExistsException, InvalidFieldException {
        if(existsApartment(apartmentDto)) {
            throw new EntityAlreadyExistsException("Não foi possível cadastrar, pois já existe um apartamento com esse número e bloco nesse condomínio!");
        }

        if(apartmentDto.getBlock() == null || apartmentDto.getBlock().isEmpty()) {
           throw new InvalidFieldException("O campo bloco não pode ser nulo ou vazio");
        }

        if(apartmentDto.getNumber() <= 0) {
            throw new InvalidFieldException("O campo número não pode ser negativo ou igual a zero");
        }

        if(apartmentDto.getResidentOwnerCpf() == null || apartmentDto.getResidentOwnerCpf().isEmpty()) {
            throw new InvalidFieldException("O campo CPF não pode ser nulo ou vazio");
        }
    }

    public boolean existsApartment(ApartmentDto apartmentDto) {
        return apartmentRepository.existsApartment(apartmentDto.getBlock(), apartmentDto.getNumber(), apartmentDto.getPlace_id());
    }
}
