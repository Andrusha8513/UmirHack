package Chackaton.com.Warehouse.StorangeZone.Rack;

import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import org.springframework.stereotype.Service;

@Service
public class RackService {

    private final RackRepository rackRepository;

    public RackService(RackRepository  rackRepository){
        this.rackRepository = rackRepository;
    }

    public Rack createRack(Rack rack , StorageZone storageZone){
        if(rackRepository.existsByZoneAndCode(rack.getZone()  , rack.getCode())){
            throw new RuntimeException("Стеллаж с кодом " + rack.getCode() + "уже существует в это зоне");
        }
        rack.setZone(storageZone);
        return rackRepository.save(rack);
    }

    public Rack findById(Long id){
        return rackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найден стелаж с таким " + id));
    }
}
