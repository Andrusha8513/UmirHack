package Chackaton.com.Warehouse;

import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse updateWarehouse(Warehouse warehouse) {
        Warehouse updateWarehouse = warehouseRepository.findById(warehouse.getId())
                .orElseThrow(() -> new RuntimeException("Склад не найден"));

        updateWarehouse.setName(warehouse.getName());
        updateWarehouse.setAddress(warehouse.getAddress());
        return warehouseRepository.save(updateWarehouse);
    }


    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouseRepository.existsByNameAndOrganization(warehouse.getName(), warehouse.getOrganization())) {
            throw new IllegalArgumentException("Склад с таким названием уже существует в этой организации");
        }
        return warehouseRepository.save(warehouse);

    }

    public Warehouse findById(Long warehouseId){
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Скалада с таким " + warehouseId + "не существует"));
    }


}
