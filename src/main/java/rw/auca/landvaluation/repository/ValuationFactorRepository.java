package rw.auca.landvaluation.repository;

import rw.auca.landvaluation.domain.ValuationFactor;
import rw.auca.landvaluation.domain.ValuationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ValuationFactorRepository extends JpaRepository<ValuationFactor, UUID> {

    @Query("SELECT vf FROM ValuationFactor vf WHERE vf.valuationRequest.landowner.nationalId = :nationalId ORDER BY vf.assessedDate DESC")
    List<ValuationFactor> findByLandownerNationalId(@Param("nationalId") String nationalId);

    boolean existsByValuationRequest(ValuationRequest valuationRequest);
}
