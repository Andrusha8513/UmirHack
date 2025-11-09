package Chackaton.com.Warehouse.Stock;

import Chackaton.com.DTO.StockItemDto;
import Chackaton.com.Organization.Organization;
import Chackaton.com.Product.Product;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockService {
    private final StockItemRepository stockItemRepository;

    public StockService(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    // Добавить товар на полку
    @Transactional
    public StockItem addProductToShelf(Product product, Shelf shelf, Integer quantity, Organization organization) {
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

    // Найти все места хранения товара в организации
    public List<StockItem> findProductLocations(Product product, Organization organization) {
        return stockItemRepository.findByProductAndOrganization(product, organization);
    }

    // Получить общее количество товара в организации
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

}