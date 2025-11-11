package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    boolean existsByRackAndCode(Rack rack, String code);
    Optional<Shelf> findByRackAndCode(Rack rack, String code);

    // Найти полку по коду
    Optional<Shelf> findByCode(String code);

    // Найти все полки стеллажа
    List<Shelf> findByRack(Rack rack);

    @Query("SELECT r FROM Shelf r WHERE r.rack.id = :rackId")
    List<Shelf> findByRackId(@Param("zoneId") Long rackId);
}
