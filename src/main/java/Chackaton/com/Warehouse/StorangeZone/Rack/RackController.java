package Chackaton.com.Warehouse.StorangeZone.Rack;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/racks")
public class RackController {

    private final RackService rackService;

    public RackController (RackService rackService){
        this.rackService = rackService;
    }

    @PostMapping
    public ResponseEntity<?> createRack(@RequestBody Rack rack){
        try {
           Rack rack1 =  rackService.createRack(rack);
            return ResponseEntity.ok(rack1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Соболезную , ошибка");
        }

    }

}
