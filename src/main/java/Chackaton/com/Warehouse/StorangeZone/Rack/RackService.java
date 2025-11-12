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
        if (rack.getCode() == null || rack.getCode().trim().isEmpty()) {
            rack.setCode(generateRackCode(storageZone));
        }

        if (rackRepository.existsByZoneAndCode(storageZone, rack.getCode()))  { //надо затестить может быть вместо storageZone должно быть rack.getZone
            throw new RuntimeException("Стеллаж с кодом " + rack.getCode() + "уже существует в это зоне");
        }
        rack.setZone(storageZone);
        return rackRepository.save(rack);
    }

    private String generateRackCode(StorageZone zone) {
        String zoneCode = zone.getName().replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        if (zoneCode.length() > 5) {
            zoneCode = zoneCode.substring(0, 5);
        }

        // получаю епта  количество стеллажей в зоне для следующего номера
        long rackCount = rackRepository.countByZone(zone);
        String rackNumber = String.format("%03d", rackCount + 1);

        return zoneCode + "-" + rackNumber;
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
        existingRack.setTotalLevels(rackDetails.getTotalLevels());
        existingRack.setMaxWeight(rackDetails.getMaxWeight());
        existingRack.setHeight(rackDetails.getHeight());
        existingRack.setWidth(rackDetails.getWidth());
        existingRack.setDepth(rackDetails.getDepth());
        existingRack.setTypes(rackDetails.getTypes());


        return rackRepository.save(existingRack);
    }
}
