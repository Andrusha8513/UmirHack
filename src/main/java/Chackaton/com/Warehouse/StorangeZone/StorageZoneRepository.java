package Chackaton.com.Warehouse.StorangeZone;

import Chackaton.com.Warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageZoneRepository extends JpaRepository<StorageZone, Long> {

    // Проверить существование зоны с таким именем в рамках склада
    boolean existsByNameAndWarehouse(String name, Warehouse warehouse);

    // Найти все зоны склада
    List<StorageZone> findByWarehouse(Warehouse warehouse);

    // Найти зону по имени и складу
    Optional<StorageZone> findByNameAndWarehouse(String name, Warehouse warehouse);
}