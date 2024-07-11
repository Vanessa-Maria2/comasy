package pds.comasy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "condominium")
public class Condominium extends Place {

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private int qtyBlocks;

    @Column(nullable = false)
    private int qtyApartments;

    @OneToMany(mappedBy = "condominium", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartmentList;
}