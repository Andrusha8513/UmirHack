package Chackaton.com.Warehouse.StorangeZone;

import Chackaton.com.Warehouse.Warehouse;
import Chackaton.com.Warehouse.WarehouseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storage-zones")

public class StorageZoneController {

    private final StorageZoneService storageZoneService;
    private final WarehouseService warehouseService;

    public StorageZoneController(StorageZoneService storageZoneService ,
                                 WarehouseService warehouseService){
        this.storageZoneService = storageZoneService;
        this.warehouseService = warehouseService;
    }

    @PostMapping("/{warehouseId}")
    public ResponseEntity<StorageZone> createStorageZone(
            @PathVariable Long warehouseId,
            @RequestBody StorageZone storageZone) {

        Warehouse warehouse = warehouseService.findById(warehouseId);
        StorageZone created = storageZoneService.createStorageZone(storageZone, warehouse);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{zoneId}")
    public ResponseEntity<StorageZone> updateStorageZone(
            @PathVariable Long zoneId,
            @RequestBody StorageZone storageZone) {

        StorageZone updated = storageZoneService.updateStorageZone(zoneId, storageZone);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{zoneId}")
    public ResponseEntity<Void> deleteStorageZone(@PathVariable Long zoneId) {
        storageZoneService.deleteStorageZone(zoneId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<StorageZone>> getZonesForWarehouse(@PathVariable Long warehouseId) {
        // Нужен метод в StorageZoneRepository: findByWarehouseId(Long warehouseId)
        List<StorageZone> zones = storageZoneService.findByWarehouse(warehouseId);
        return ResponseEntity.ok(zones);
    }
}