package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shelf")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService){
        this.shelfService = shelfService;
    }

    @PostMapping
    public ResponseEntity<?> createShelf(@RequestBody Shelf shelf){
        try {
           Shelf shelf1 =  shelfService.createShelf(shelf);
            return ResponseEntity.ok(shelf1);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
