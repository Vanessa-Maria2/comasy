package pds.comasy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceDTO {
    private String name;
    private String number;
    private String telephoneNumber;
    private String zipcode;
    private String neighborhood;
    private String streetAddress;
    private String city;
    private String state;

    private String cnpj; // Only for Hostel and Condominium
    private int capacity; // Only for Hostel
    private int qtyRooms; // Only for Republic and Hostel
    private double rentPrice; // Only for Republic
    private int qtyBlocks; // Only for Condominium
    private int qtyApartments; // Only for Condominium
    private List<ApartmentDto> apartmentDtos; // Only for Condominium

    private String residentOwnerCpf; // Only for Hostel
}

