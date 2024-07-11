package pds.comasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pds.comasy.entity.Hostel;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {
}
