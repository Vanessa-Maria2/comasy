package pds.comasy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "republic")
public class Republic extends Place {

    @Column(nullable = false)
    private int qtyRooms;

    @Column(nullable = false)
    private double rentPrice;
}
