package Chackaton.com.Warehouse.StorangeZone.Rack;

import org.springframework.stereotype.Service;

@Service
public class RackService {

    private final RackRepository rackRepository;

    public RackService(RackRepository  rackRepository){
        this.rackRepository = rackRepository;
    }

    public Rack createRack(Rack rack){
        if(rackRepository.existsByZoneAndCode(rack.getZone()  , rack.getCode())){
            throw new RuntimeException("Стеллаж с кодом " + rack.getCode() + "уже существует в это зоне");
        }
        return rackRepository.save(rack);
    }
}
