package rw.auca.landvaluation.repository;

import rw.auca.landvaluation.domain.Landowner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LandownerRepository extends JpaRepository<Landowner, UUID> {

    List<Landowner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT vr.landowner FROM ValuationRequest vr WHERE vr.valuer.id = :valuerId")
    List<Landowner> findLandownersByValuerId(@Param("valuerId") UUID valuerId);
}
