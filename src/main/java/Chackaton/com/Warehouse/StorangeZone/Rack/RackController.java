package Chackaton.com.Warehouse.StorangeZone.Rack;

import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import Chackaton.com.Warehouse.StorangeZone.StorageZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/racks")
public class RackController {

    private final RackService rackService;
    private final StorageZoneService storageZoneService;

    public RackController (RackService rackService,
                           StorageZoneService storageZoneService){
        this.rackService = rackService;
        this.storageZoneService = storageZoneService;
    }

    @PostMapping("{storageZoneId}")
    public ResponseEntity<?> createRack(@RequestBody Rack rack ,
                                       @PathVariable Long storageZoneId){
        try {
            StorageZone storageZone = storageZoneService.findById(storageZoneId);
           Rack rack1 =  rackService.createRack(rack ,storageZone);
            return ResponseEntity.ok(rack1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Соболезную , ошибка");
        }

    }

}
