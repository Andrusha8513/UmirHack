package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import org.springframework.stereotype.Service;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfService(ShelfRepository shelfRepository){
        this.shelfRepository = shelfRepository;
    }

    public Shelf createShelf(Shelf shelf){
        if(shelfRepository.existsByRackAndCode(shelf.getRack() , shelf.getCode())){
            throw new RuntimeException("Полка с кодом " + shelf.getCode() + "  ужу существует в этом стелаже");
        }
        return shelfRepository.save(shelf);
    }

}
