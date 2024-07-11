package pds.comasy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String telephoneNumber;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;
}

