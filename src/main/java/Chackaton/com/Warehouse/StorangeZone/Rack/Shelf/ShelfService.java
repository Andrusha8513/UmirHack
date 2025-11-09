package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import org.springframework.stereotype.Service;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfService(ShelfRepository shelfRepository){
        this.shelfRepository = shelfRepository;
    }

    public Shelf createShelf(Shelf shelf  , Rack rack){
        if(shelfRepository.existsByRackAndCode(shelf.getRack() , shelf.getCode())){
            throw new RuntimeException("Полка с кодом " + shelf.getCode() + "  ужу существует в этом стелаже");
        }
        shelf.setRack(rack);
        return shelfRepository.save(shelf);
    }





}
