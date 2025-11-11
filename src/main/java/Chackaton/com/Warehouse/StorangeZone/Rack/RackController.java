package Chackaton.com.Warehouse.StorangeZone.Rack;

import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import Chackaton.com.Warehouse.StorangeZone.StorageZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/racks")
public class RackController {

    private final RackService rackService;
    private final StorageZoneService storageZoneService;

    public RackController(RackService rackService,
                          StorageZoneService storageZoneService) {
        this.rackService = rackService;
        this.storageZoneService = storageZoneService;
    }

    @PostMapping("{storageZoneId}")
    public ResponseEntity<?> createRack(@RequestBody Rack rack,
                                        @PathVariable Long storageZoneId, ZoneId zoneId) {
        try {
            StorageZone storageZone = storageZoneService.findById(storageZoneId);
            Rack rack1 = rackService.createRack(rack, storageZone);
            return ResponseEntity.ok(rack1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Соболезную , ошибка");
        }

    }

    //збс
    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<Rack>> getRacksForZone(@PathVariable Long zoneId) {
        List<Rack> racks = rackService.findByZone(zoneId);
        return ResponseEntity.ok(racks);


    }
//збс
    @PutMapping("/{rackId}")
    public ResponseEntity<?> updateRack(@PathVariable Long rackId, @RequestBody Rack rackDetails) {
        // Нужен метод updateRack в RackService
        Rack updatedRack = rackService.updateRack(rackId, rackDetails);
        return ResponseEntity.ok(updatedRack);

    }
}
