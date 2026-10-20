package rw.auca.landvaluation.repository;

import rw.auca.landvaluation.domain.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType, UUID> {

    @Query("SELECT pt FROM PropertyType pt WHERE pt.valuers IS EMPTY")
    List<PropertyType> findUnusedPropertyTypes();
}
