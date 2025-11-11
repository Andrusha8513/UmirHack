package Chackaton.com.Warehouse.StorangeZone.Rack;

import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import Chackaton.com.Warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RackRepository extends JpaRepository<Rack , Long> {
    boolean existsByZoneAndCode(StorageZone storageZone, String code);

    Optional<Rack> findByZoneAndCode(StorageZone storageZone, String code);

    List<Rack> findByZone(StorageZone zone);

    @Query("SELECT r FROM Rack r WHERE r.zone.id = :zoneId")
    List<Rack> findByZoneId(@Param("zoneId") Long zoneId);
}
