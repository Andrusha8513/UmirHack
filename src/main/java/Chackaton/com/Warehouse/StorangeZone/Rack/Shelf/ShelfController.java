package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import Chackaton.com.Warehouse.StorangeZone.Rack.RackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shelf")
public class ShelfController {

    private final ShelfService shelfService;
    private final RackService rackService;

    public ShelfController(ShelfService shelfService,
                           RackService rackService){
        this.shelfService = shelfService;
        this.rackService = rackService;
    }

    @PostMapping("{rackId}")
    public ResponseEntity<?> createShelf(@PathVariable Long rackId,@RequestBody Shelf shelf){
        try {
            Rack rack = rackService.findById(rackId);
           Shelf shelf1 =  shelfService.createShelf(shelf , rack);
            return ResponseEntity.ok(shelf1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
