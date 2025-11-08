package Chackaton.com.Warehouse.StorangeZone.Rack;

import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import Chackaton.com.Warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RackRepository extends JpaRepository<Rack , Long> {
    boolean existsByWarehouseAndCode(StorageZone storageZone, String code);

    Optional<Rack> findByWarehouseAndCode(StorageZone storageZone, String code);

    List<Rack> findByZone(StorageZone zone);
}
