package Chackaton.com.Warehouse;


import Chackaton.com.Organization.Organization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")

public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService){
        this.warehouseService = warehouseService;
    }
    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse , Organization organization) {
        Warehouse created = warehouseService.createWarehouse(warehouse , organization);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/organization/{organizationId}")
    public ResponseEntity<Warehouse> createWarehouse(
            @RequestBody Warehouse warehouse,
            @PathVariable Long organizationId) {
        Organization organization = new Organization();
        organization.setId(organizationId);
        Warehouse created = warehouseService.createWarehouse(warehouse, organization);
        return ResponseEntity.ok(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        warehouse.setId(id);
        Warehouse updated = warehouseService.updateWarehouse(warehouse);
        return ResponseEntity.ok(updated);
    }

    }



