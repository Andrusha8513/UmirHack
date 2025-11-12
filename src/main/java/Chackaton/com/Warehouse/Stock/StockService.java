package Chackaton.com.Warehouse.Stock;

import Chackaton.com.DTO.RackLoadInfo;
import Chackaton.com.DTO.ShelfLoadInfo;
import Chackaton.com.DTO.StockItemDto;
import Chackaton.com.Organization.Organization;
import Chackaton.com.Product.Product;
import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.ShelfRepository;
import Chackaton.com.Warehouse.Warehouse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockItemRepository stockItemRepository;
    private final ShelfRepository shelfRepository;

    public StockService(StockItemRepository stockItemRepository,
                        ShelfRepository shelfRepository) {
        this.stockItemRepository = stockItemRepository;
        this.shelfRepository = shelfRepository;
    }

    @Transactional
    public StockItem addProductToShelf(Product product, Shelf shelf, Integer quantity, Organization organization) {

        Warehouse warehouse = shelf.getRack().getZone().getWarehouse();
        Double maxVolume = warehouse.getMaxVolume();


        Double addVolume = product.getVolume() * quantity;
        if (addVolume == null || addVolume <= 0) {
            throw new IllegalArgumentException("Невозможно добавить товар: не указан объем продукта.");
        }
        //текущи занятый объём
        Double currentVolume = stockItemRepository.getTotalVolumeInWarehouse(warehouse);
        if (currentVolume == null) {
            currentVolume = 0.0;
        }
        if (currentVolume + addVolume > maxVolume) {
            throw new RuntimeException(
                    String.format("Ошибка: Превышен максимальный объем склада. Текущий: %.2f м³, Попытка добавить: %.2f м³, Максимум: %.2f м³",
                            currentVolume, addVolume, maxVolume)
            );
        }


        // Проверяем, есть ли уже этот товар на этой полке
        StockItem existingStock = stockItemRepository.findByProductAndShelf(product, shelf)
                .orElse(null);

        if (existingStock != null) {
            // Обновляем количество
            existingStock.setQuantity(existingStock.getQuantity() + quantity);
            return stockItemRepository.save(existingStock);
        } else {
            // Создаем новую запись
            StockItem newStock = new StockItem(product, shelf, quantity, organization);
            newStock.setStatus(StockItemStatus.IN_SORTING);
            return stockItemRepository.save(newStock);
        }
    }

    // Удалить товар с полки
    @Transactional
    public StockItem removeProductFromShelf(Product product, Shelf shelf, Integer quantity) {
        StockItem stockItem = stockItemRepository.findByProductAndShelf(product, shelf)
                .orElseThrow(() -> new RuntimeException("Товар не найден на указанной полке"));

        if (stockItem.getQuantity() < quantity) {
            throw new RuntimeException("Недостаточно товара на полке. Доступно: " + stockItem.getQuantity());
        }

        stockItem.setQuantity(stockItem.getQuantity() - quantity);
        stockItem.setStatus(StockItemStatus.SHIPPED);

        // Если количество стало 0, удаляем запись
        if (stockItem.getQuantity() == 0) {
            stockItemRepository.delete(stockItem);
            return null;
        }

        return stockItemRepository.save(stockItem);
    }

    // Переместить товар между полками
    @Transactional
    public void moveProductBetweenShelves(Product product, Shelf fromShelf, Shelf toShelf, Integer quantity, Organization organization) {
        removeProductFromShelf(product, fromShelf, quantity);
        addProductToShelf(product, toShelf, quantity, organization);
    }


    public List<StockItem> findProductLocations(Product product, Organization organization) {
        return stockItemRepository.findByProductAndOrganization(product, organization);
    }

    // получить общее количество товара в организации
    public Integer getTotalProductQuantity(Product product, Organization organization) {
        List<StockItem> stockItems = stockItemRepository.findByProductAndOrganization(product, organization);
        return stockItems.stream()
                .mapToInt(StockItem::getQuantity)
                .sum();
    }

//    // Поиск по штрих-коду
//    public List<StockItem> findByBarcode(String barcode, Organization organization) {
//        return stockItemRepository.findByProductBarcodeAndOrganization(barcode, organization);
//    }

    public StockItemDto toStockItemDto(StockItem stockItem) {
        StockItemDto dto = new StockItemDto();
        dto.setId(stockItem.getId());
        dto.setProductId(stockItem.getProduct().getId());
        dto.setProductName(stockItem.getProduct().getName());
        dto.setShelfId(stockItem.getShelf().getId());
        dto.setShelfCode(stockItem.getShelf().getCode());
        dto.setQuantity(stockItem.getQuantity());
        dto.setOrganizationId(stockItem.getOrganization().getId());
        dto.setBatchNumber(stockItem.getBatchNumber());
        dto.setExpiryDate(stockItem.getExpiryDate());
        dto.setManufacturedDate(stockItem.getManufacturedDate());
        dto.setCreatedAt(stockItem.getCreatedAt());
        dto.setUpdatedAt(stockItem.getUpdatedAt());

        // Дополнительная информация о местоположении
        if (stockItem.getShelf() != null && stockItem.getShelf().getRack() != null
                && stockItem.getShelf().getRack().getZone() != null
                && stockItem.getShelf().getRack().getZone().getWarehouse() != null) {

            dto.setWarehouseName(stockItem.getShelf().getRack().getZone().getWarehouse().getName());
            dto.setZoneName(stockItem.getShelf().getRack().getZone().getName());
            dto.setRackCode(stockItem.getShelf().getRack().getCode());
        }

        return dto;
    }

    // Расчет загрузки полки
    public ShelfLoadInfo calculateShelfLoad(Shelf shelf) {
        List<StockItem> stockItems = stockItemRepository.findByShelf(shelf);

        double totalWeight = 0.0;
        double totalVolume = 0.0;
        int totalItems = 0;

        for (StockItem item : stockItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product.getWeight() != null) {
                totalWeight += product.getWeight() * quantity;
            }
            if (product.getVolume() != null) {
                totalVolume += product.getVolume() * quantity;
            }
            totalItems += quantity;
        }

        return new ShelfLoadInfo(shelf, totalWeight, totalVolume, totalItems);
    }

    public RackLoadInfo calculateRackLoad(Rack rack) {
        List<Shelf> shelves = shelfRepository.findByRack(rack);

        // Загружаем все StockItem для всех полок стеллажа одним запросом
        List<StockItem> allStockItems = stockItemRepository.findByShelvesWithProduct(shelves);

        // Группируем по полкам для быстрого доступа
        Map<Long, List<StockItem>> stockByShelf = allStockItems.stream()
                .collect(Collectors.groupingBy(item -> item.getShelf().getId()));

        double totalWeight = 0.0;
        double totalVolume = 0.0;
        int totalItems = 0;
        Map<Long, ShelfLoadInfo> shelfLoads = new HashMap<>();

        for (Shelf shelf : shelves) {
            List<StockItem> shelfItems = stockByShelf.getOrDefault(shelf.getId(), new ArrayList<>());
            ShelfLoadInfo shelfLoad = calculateShelfLoadFromItems(shelf, shelfItems);
            totalWeight += shelfLoad.getTotalWeight();
            totalVolume += shelfLoad.getTotalVolume();
            totalItems += shelfLoad.getTotalItems();
            shelfLoads.put(shelf.getId(), shelfLoad);
        }

        return new RackLoadInfo(rack, totalWeight, totalVolume, totalItems, shelfLoads);
    }

    private ShelfLoadInfo calculateShelfLoadFromItems(Shelf shelf, List<StockItem> stockItems) {
        double totalWeight = 0.0;
        double totalVolume = 0.0;
        int totalItems = 0;

        for (StockItem item : stockItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product.getWeight() != null) {
                totalWeight += product.getWeight() * quantity;
            }
            if (product.getVolume() != null) {
                totalVolume += product.getVolume() * quantity;
            }
            totalItems += quantity;
        }

        return new ShelfLoadInfo(shelf, totalWeight, totalVolume, totalItems);
    }

    // Новый метод для массового преобразования
    public List<StockItemDto> toStockItemDtoList(List<StockItem> stockItems) {
        // Предполагаем, что stockItems уже загружены с JOIN FETCH
        return stockItems.stream()
                .map(this::toStockItemDto)
                .collect(Collectors.toList());
    }


}