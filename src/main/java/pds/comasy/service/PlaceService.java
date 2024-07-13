package pds.comasy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pds.comasy.dto.PlaceDTO;
import pds.comasy.entity.Condominium;
import pds.comasy.entity.Hostel;
import pds.comasy.entity.Place;
import pds.comasy.entity.Republic;
import pds.comasy.exceptions.InvalidFieldException;
import pds.comasy.exceptions.NotFoundException;
import pds.comasy.patterns.placeStrategy.CondominiumStrategy;
import pds.comasy.patterns.placeStrategy.HostelStrategy;
import pds.comasy.patterns.placeStrategy.PlaceStrategy;
import pds.comasy.patterns.placeStrategy.RepublicStrategy;
import pds.comasy.repository.ApartmentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PlaceService {

    @Autowired
    private CondominiumStrategy condominiumStrategy;

    @Autowired
    private HostelStrategy hostelStrategy;

    @Autowired
    private RepublicStrategy republicStrategy;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Value("${systemType}")
    private String systemType;

    private final Map<String, PlaceStrategy> strategyMap;

    @Autowired
    public PlaceService(CondominiumStrategy condominiumStrategy, HostelStrategy hostelStrategy, RepublicStrategy republicStrategy, ApartmentRepository apartmentRepository) {
        this.condominiumStrategy = condominiumStrategy;
        this.hostelStrategy = hostelStrategy;
        this.republicStrategy = republicStrategy;
        this.apartmentRepository = apartmentRepository;

        strategyMap = new HashMap<>();
        strategyMap.put("condominium", condominiumStrategy);
        strategyMap.put("hostel", hostelStrategy);
        strategyMap.put("republic", republicStrategy);
    }

    public void savePlace(PlaceDTO placeDto) throws InvalidFieldException {
        PlaceStrategy strategy = strategyMap.get(systemType);

        if (strategy == null) {
            throw new InvalidFieldException("Invalid system type");
        }

        validateCommonFields(placeDto);
        strategy.validateFields(placeDto);

        Place place = strategy.createPlaceEntity();
        strategy.mapPlaceDtoToEntity(placeDto, place);
        strategy.savePlace(place);
    }

    private void validateCommonFields(PlaceDTO placeDto) throws InvalidFieldException {
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
    }

    public List<? extends Place> getAllPlaces() throws NotFoundException {
        PlaceStrategy strategy = strategyMap.get(systemType);

        if (strategy == null) {
            throw new NotFoundException("Invalid system type");
        }

        return strategy.getAllPlaces();
    }
}
