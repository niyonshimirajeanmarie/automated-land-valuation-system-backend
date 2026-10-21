package rw.auca.landvaluation.repository;

import rw.auca.landvaluation.domain.ValuationRequest;
import rw.auca.landvaluation.domain.ValuationStatus;
import rw.auca.landvaluation.domain.Valuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ValuationRequestRepository extends JpaRepository<ValuationRequest, UUID> {

    List<ValuationRequest> findByStatusOrderByRequestDateAsc(ValuationStatus status);

    List<ValuationRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

    boolean existsByValuerAndRequestDateAndStatusNot(Valuer valuer, LocalDate requestDate, ValuationStatus status);

    @Query("SELECT vr.status, COUNT(vr) FROM ValuationRequest vr GROUP BY vr.status")
    List<Object[]> countValuationRequestsByStatus();

    @Modifying
    @Query("UPDATE ValuationRequest vr SET vr.status = :declinedStatus WHERE vr.valuer.id = :valuerId AND vr.requestDate = :date AND vr.status != :valuedStatus")
    int cancelDay(@Param("valuerId") UUID valuerId,
                  @Param("date") LocalDate date,
                  @Param("declinedStatus") ValuationStatus declinedStatus,
                  @Param("valuedStatus") ValuationStatus valuedStatus);

    long countByRequestDate(LocalDate requestDate);

    long countByStatus(ValuationStatus status);
}
