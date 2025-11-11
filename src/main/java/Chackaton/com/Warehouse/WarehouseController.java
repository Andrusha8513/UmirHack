package Chackaton.com.Warehouse;


import Chackaton.com.Organization.Organization;
import Chackaton.com.Organization.OrganizationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")

public class WarehouseController {

    private final WarehouseService warehouseService;
    private final OrganizationRepository organizationRepository;

    public WarehouseController(WarehouseService warehouseService,
                               OrganizationRepository organizationRepository){
        this.warehouseService = warehouseService;
        this.organizationRepository = organizationRepository;
    }
//    @PostMapping
//    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse , Organization organization) {
//        Warehouse created = warehouseService.createWarehouse(warehouse , organization);
//        return ResponseEntity.ok(created);
//    }

    @PostMapping("/organization/{organizationId}")
    public ResponseEntity<?> createWarehouse(
            @RequestBody Warehouse warehouse,
            @PathVariable Long organizationId) {
        try {
            // Получаем реальную организацию из базы
            Organization organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new IllegalArgumentException("Организация не найдена"));

            Warehouse created = warehouseService.createWarehouse(warehouse, organization);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        warehouse.setId(id);
        Warehouse updated = warehouseService.updateWarehouse(warehouse);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Warehouse>> getWarehousesForOrganization(@PathVariable Long organizationId) {
        // Создаем объект-заглушку Organization для передачи в Service
        Organization organization = new Organization();
        organization.setId(organizationId);

        List<Warehouse> warehouses = warehouseService.findAllByOrganization(organization);
        return ResponseEntity.ok(warehouses);
    }

    // 3. НОВЫЙ Эндпоинт для удаления склада
    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable Long warehouseId) {
        try {
            warehouseService.deleteWarehouse(warehouseId);
            return ResponseEntity.ok().body("Склад успешно удален.");
        } catch (IllegalArgumentException e) {
            // Если склада с таким ID нет
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Если есть связанные сущности (зоны, товары) и удаление заблокировано
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Невозможно удалить склад: " + e.getMessage());
        }
    }

    }



