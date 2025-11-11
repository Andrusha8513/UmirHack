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
    Optional<Shelf> findByCode(String code);
    List<Shelf> findByRack(Rack rack);

    @Query("SELECT r FROM Shelf r WHERE r.rack.id = :rackId")
    List<Shelf> findByRackId(@Param("rackId") Long rackId);

    // Добавляем недостающие методы
    @Query("SELECT MAX(s.level) FROM Shelf s WHERE s.rack = :rack")
    Integer findMaxLevelByRack(@Param("rack") Rack rack);

    @Query("SELECT MAX(s.position) FROM Shelf s WHERE s.rack = :rack AND s.level = :level")
    Integer findMaxPositionByRackAndLevel(@Param("rack") Rack rack, @Param("level") Integer level);
}
