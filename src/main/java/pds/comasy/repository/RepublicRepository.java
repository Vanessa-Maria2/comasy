package pds.comasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pds.comasy.entity.Republic;

@Repository
public interface RepublicRepository extends JpaRepository<Republic, Long> {
}
