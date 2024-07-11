package pds.comasy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hostel")
public class Hostel extends Place {

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int qtyRooms;

    @Column(nullable = false)
    private String residentOwnerCpf;
}
