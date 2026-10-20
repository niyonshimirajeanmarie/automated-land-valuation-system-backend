package rw.auca.landvaluation.repository;

import rw.auca.landvaluation.domain.DistrictOffice;
import rw.auca.landvaluation.domain.Valuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ValuerRepository extends JpaRepository<Valuer, UUID> {

    @Query("SELECT v FROM Valuer v JOIN v.propertyTypes pt WHERE LOWER(pt.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Valuer> findByPropertyTypeName(@Param("name") String name);

    boolean existsByDistrictOffice(DistrictOffice districtOffice);
}
