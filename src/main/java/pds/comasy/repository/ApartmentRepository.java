package pds.comasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pds.comasy.entity.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Apartment a WHERE a.block = ?1 AND a.number = ?2 AND a.place_id =?3 ")
    boolean existsApartment(String block, int number, Long placeId);

}
