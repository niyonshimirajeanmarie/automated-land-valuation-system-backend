package rw.auca.landvaluation.repository;

import rw.auca.landvaluation.domain.DistrictOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DistrictOfficeRepository extends JpaRepository<DistrictOffice, UUID> {

    @Query("SELECT d FROM DistrictOffice d WHERE NOT EXISTS (SELECT v FROM Valuer v WHERE v.districtOffice = d) ORDER BY d.officeNumber ASC")
    List<DistrictOffice> findFreeDistrictOffices();

    Optional<DistrictOffice> findByOfficeNumber(int officeNumber);
}
