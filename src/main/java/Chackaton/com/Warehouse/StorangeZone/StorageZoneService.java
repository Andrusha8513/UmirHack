package Chackaton.com.Warehouse.StorangeZone;

import Chackaton.com.Warehouse.Warehouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageZoneService {

    private final StorageZoneRepository storageZoneRepository;

    public StorageZoneService(StorageZoneRepository storageZoneRepository) {
        this.storageZoneRepository = storageZoneRepository;
    }

    public StorageZone createStorageZone(StorageZone storageZone, Warehouse warehouse) {


        if (storageZoneRepository.existsByNameAndWarehouse(storageZone.getName(), warehouse)) {
            throw new IllegalArgumentException("Зона с названием '" + storageZone.getName() + "' уже существует на этом складе");
        }

        storageZone.setWarehouse(warehouse);

        return storageZoneRepository.save(storageZone);
    }

    public StorageZone updateStorageZone(Long zoneId, StorageZone updatedZone) {
        StorageZone existingZone = storageZoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Зона не найдена"));
if(storageZoneRepository.existsByNameAndWarehouse(updatedZone.getName() , updatedZone.getWarehouse())){
    throw new IllegalArgumentException("Зона с таким названием уже существует ");
}

        existingZone.setName(updatedZone.getName());
        existingZone.setDescription(updatedZone.getDescription());
        existingZone.setStorageTypes(updatedZone.getStorageTypes());

        return storageZoneRepository.save(existingZone);
    }

    public void deleteStorageZone(Long zoneId) {
        StorageZone zone = storageZoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Зона не найдена"));


        if (!zone.getRacks().isEmpty()) {
            throw new RuntimeException("Невозможно удалить зону: имеются связанные стеллажи");
        }

        storageZoneRepository.delete(zone);
    }

    public StorageZone findById(Long id) {
        return storageZoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Не найдена зона с таким " + id));
    }

    //вроде збс
    public List<StorageZone> findByWarehouse(Long warehouseId) {
        return storageZoneRepository.findByWarehouseId(warehouseId);
    }
}