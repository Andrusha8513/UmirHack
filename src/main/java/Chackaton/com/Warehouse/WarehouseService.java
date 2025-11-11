package Chackaton.com.Warehouse;

import Chackaton.com.Organization.Organization;
import Chackaton.com.Organization.OrganizationRepository;
import Chackaton.com.Warehouse.Stock.StockItemRepository;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.ShelfRepository;
import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import Chackaton.com.Warehouse.StorangeZone.StorageZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final StockItemRepository stockItemRepository;
    private final StorageZoneRepository storageZoneRepository;
    private final ShelfRepository shelfRepository;
    private final OrganizationRepository organizationRepository;
    public WarehouseService(WarehouseRepository warehouseRepository,
                            StockItemRepository stockItemRepository,
                            StorageZoneRepository storageZoneRepository,
                            ShelfRepository shelfRepository,
                            OrganizationRepository organizationRepository) {
        this.warehouseRepository = warehouseRepository;
        this.stockItemRepository = stockItemRepository;
        this.storageZoneRepository = storageZoneRepository;
        this.shelfRepository = shelfRepository;
        this.organizationRepository = organizationRepository;
    }

    public Warehouse updateWarehouse(Warehouse warehouse) {
        Warehouse updateWarehouse = warehouseRepository.findById(warehouse.getId())
                .orElseThrow(() -> new RuntimeException("Склад не найден"));

        updateWarehouse.setName(warehouse.getName());
        updateWarehouse.setAddress(warehouse.getAddress());
        return warehouseRepository.save(updateWarehouse);
    }


    public Warehouse createWarehouse(Warehouse warehouse, Organization organization) {

        if (!organizationRepository.existsById(organization.getId())) {
            throw new IllegalArgumentException("Организация не существует");
        }

        if (warehouse.getMaxVolume() == null || warehouse.getMaxVolume() <= 0) {
            throw new IllegalArgumentException("Максимальный объем склада должен быть указан и больше 0");
        }

        if (warehouse.getTotalCapacity() != null && warehouse.getTotalCapacity() < 0) {
            throw new IllegalArgumentException("Общая вместимость не может быть отрицательной");
        }

        if (warehouseRepository.existsByNameAndOrganization(warehouse.getName(), warehouse.getOrganization())) {
            throw new IllegalArgumentException("Склад с таким названием уже существует в этой организации");
        }
        warehouse.setOrganization(organization);
        warehouse.setCurrentCapacity(0);
        return warehouseRepository.save(warehouse);

    }

    public Warehouse findById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Скалада с таким " + warehouseId + "не существует"));
    }

    public void deleteWarehouse(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new IllegalArgumentException("Склада с таким " + id + "нет");
        }
        warehouseRepository.deleteById(id);
        ;

    }

    public List<Warehouse> findAllByOrganization(Organization organization) {
        // Используем добавленный в репозиторий метод
        return warehouseRepository.findByOrganization(organization);
    }


    @Transactional
    public void updateWarehouseCapacity(Long warehouseId) {
        Warehouse warehouse = findById(warehouseId);

        // Рассчитываем текущую загрузку на основе StockItem
        Double currentVolume = stockItemRepository.getTotalVolumeInWarehouse(warehouse);
        Integer currentCapacity = calculateCurrentCapacity(warehouse);

        warehouse.setCurrentCapacity(currentCapacity);
        // Можно добавить поле currentVolume если нужно
        warehouseRepository.save(warehouse);
    }

    private Integer calculateCurrentCapacity(Warehouse warehouse) {
        // Логика расчета текущей загрузки в паллетах/единицах
        // Например, на основе количества StockItem и их объема
        List<StorageZone> zones = storageZoneRepository.findByWarehouse(warehouse);
        return zones.stream()
                .mapToInt(zone -> zone.getRacks().stream()
                        .mapToInt(rack -> rack.getShelves().stream()
                                .mapToInt(shelf -> shelfRepository.findByRack(rack).size())
                                .sum())
                        .sum())
                .sum();
    }

//    // Вызывать этот метод при операциях с товарами
//    public void recalculateWarehouseMetrics(Long warehouseId) {
//        updateWarehouseCapacity(warehouseId);
//        // Можно добавить другие метрики: заполненность в %, предупреждения и т.д.
//    }

}
