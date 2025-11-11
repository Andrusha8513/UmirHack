package Chackaton.com.Warehouse.StorangeZone.Rack;

import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RackService {

    private final RackRepository rackRepository;

    public RackService(RackRepository rackRepository) {
        this.rackRepository = rackRepository;
    }

    public Rack createRack(Rack rack, StorageZone storageZone) {
        if (rackRepository.existsByZoneAndCode(rack.getZone(), rack.getCode())) {
            throw new RuntimeException("Стеллаж с кодом " + rack.getCode() + "уже существует в это зоне");
        }
        rack.setZone(storageZone);
        return rackRepository.save(rack);
    }

    public Rack findById(Long id) {
        return rackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найден стелаж с таким " + id));
    }

    public List<Rack> findByZone(Long id) {
        return rackRepository.findByZoneId(id);
    }

    public Rack updateRack(Long rackId, Rack rackDetails) {
        Rack existingRack = rackRepository.findById(rackId)
                .orElseThrow(() -> new IllegalArgumentException("Не найден стеллаж с ID: " + rackId));


        if (!existingRack.getCode().equals(rackDetails.getCode())) {
            if (rackRepository.findByZoneAndCode(existingRack.getZone(), rackDetails.getCode()).isPresent()) {
                throw new IllegalArgumentException(
                        "Стеллаж с кодом '" + rackDetails.getCode() +
                                "' уже существует в зоне '" + existingRack.getZone().getName() + "'"
                );
            }
        }


        existingRack.setCode(rackDetails.getCode());
        existingRack.setName(rackDetails.getName());
        existingRack.setRowNumber(rackDetails.getRowNumber());
        existingRack.setSectionNumber(rackDetails.getSectionNumber());
        existingRack.setTotalLevels(rackDetails.getTotalLevels());
        existingRack.setMaxWeight(rackDetails.getMaxWeight());
        existingRack.setHeight(rackDetails.getHeight());
        existingRack.setWidth(rackDetails.getWidth());
        existingRack.setDepth(rackDetails.getDepth());
        existingRack.setTypes(rackDetails.getTypes());


        return rackRepository.save(existingRack);
    }
}
