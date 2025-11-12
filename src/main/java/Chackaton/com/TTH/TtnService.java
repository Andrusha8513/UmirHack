package Chackaton.com.TTH;

import Chackaton.com.DTO.*;
import Chackaton.com.Organization.Organization;
import Chackaton.com.Organization.OrganizationRepository;
import Chackaton.com.Product.Product;
import Chackaton.com.Product.ProductRepository;
import Chackaton.com.Users.Users;
import Chackaton.com.Warehouse.Stock.StockItem;
import Chackaton.com.Warehouse.Stock.StockItemRepository;
import Chackaton.com.Warehouse.Stock.StockItemStatus;
import Chackaton.com.Warehouse.Warehouse;
import Chackaton.com.Warehouse.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TtnService {

    private final StockItemRepository stockItemRepository;
    private final ProductRepository productRepository;
    private final OrganizationRepository organizationRepository;
    private final WarehouseRepository warehouseRepository;

    public TtnService(StockItemRepository stockItemRepository,
                      ProductRepository productRepository,
                      OrganizationRepository organizationRepository,
                      WarehouseRepository warehouseRepository) {
        this.stockItemRepository = stockItemRepository;
        this.productRepository = productRepository;
        this.organizationRepository = organizationRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public TtnDocumentDto generateTtnDocument(Long shipmentId, Long organizationFromId,
                                              Long organizationToId, Long warehouseId) {

        // Получаем организации
        Organization organizationFrom = organizationRepository.findById(organizationFromId)
                .orElseThrow(() -> new IllegalArgumentException("Организация-отправитель не найдена"));
        Organization organizationTo = organizationRepository.findById(organizationToId)
                .orElseThrow(() -> new IllegalArgumentException("Организация-получатель не найдена"));

        // Получаем склад
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Склад не найден"));

        // Получаем товары для отгрузки (SHIPPED статус)
        List<StockItem> shippedItems = stockItemRepository.findByOrganizationAndStatus(
                organizationFrom, StockItemStatus.SHIPPED);

        // Преобразуем в DTO
        List<TtnProductDto> productDtos = shippedItems.stream()
                .map(this::convertToTtnProductDto)
                .collect(Collectors.toList());

        // Рассчитываем итоги
        TtnTotalsDto totals = calculateTotals(productDtos);

        // Формируем документ
        return new TtnDocumentDto(
                shipmentId,
                "TTN-" + shipmentId,
                LocalDateTime.now(),
                convertToOrganizationDto(organizationFrom),
                convertToOrganizationDto(organizationTo),
                convertToWarehouseDto(warehouse),
                "Зона отгрузки", // Можно получить из StockItem
                "SHIPPED",
                LocalDateTime.now(),
                productDtos,
                totals,
                getPersonnelInfo(), // Получаем информацию о персонале
                getSystemInfo()
        );
    }

    private TtnProductDto convertToTtnProductDto(StockItem stockItem) {
        Product product = stockItem.getProduct();

        return new TtnProductDto(
                product.getId(),
                product.getArticleNumber(),
                product.getName(),
                product.getDescription(),
                product.getManufacturer(),
                stockItem.getQuantity(),
                product.getPrice(),
                product.getPrice() * stockItem.getQuantity(),
                product.getWeight(),
                product.getVolume(),
                createLocationDto(stockItem),
                stockItem.getStatus().name(),
                "/api/images/" + (product.getPreviewImageId() != null ? product.getPreviewImageId() : "")
        );
    }

    private TtnLocationDto createLocationDto(StockItem stockItem) {
        return new TtnLocationDto(
                stockItem.getShelf().getRack().getZone().getWarehouse().getName(),
                stockItem.getShelf().getRack().getZone().getName(),
                stockItem.getShelf().getRack().getCode(),
                stockItem.getShelf().getCode()
        );
    }

    private TtnTotalsDto calculateTotals(List<TtnProductDto> products) {
        Integer quantity = products.stream().mapToInt(TtnProductDto::getQuantity).sum();
        Double amount = products.stream().mapToDouble(TtnProductDto::getTotal).sum();
        Double weight = products.stream().mapToDouble(p -> p.getWeight() * p.getQuantity()).sum();
        Double volume = products.stream().mapToDouble(p -> p.getVolume() * p.getQuantity()).sum();

        return new TtnTotalsDto(quantity, amount, weight, volume, products.size(), quantity, weight * 1.1);
    }

    private OrganizationDto convertToOrganizationDto(Organization organization) {
        return new OrganizationDto(
                organization.getId(),
                organization.getName(),
                organization.getAddress(),
                "+7 (XXX) XXX-XX-XX", // Заглушка для телефона
                "12345678" // Заглушка для ОКПО
        );
    }

    private WarehouseDto convertToWarehouseDto(Warehouse warehouse) {
        return new WarehouseDto(warehouse.getId(), warehouse.getName(), warehouse.getAddress());
    }

    private TtnPersonnelDto getPersonnelInfo() {
        // В реальной системе здесь будет логика получения данных о текущем пользователе
        return new TtnPersonnelDto(
                new TtnPersonDto("Начальник склада", "Иванов А.С."),
                new TtnPersonDto("Кладовщик", "Петрова М.И."),
                new TtnPersonDto("Кладовщик", "Сидоров В.П."),
                new TtnPersonDto("Менеджер по закупкам", "Козлов Д.А.")
        );
    }

    private TtnSystemInfoDto getSystemInfo() {
        return new TtnSystemInfoDto(
                LocalDateTime.now(),
                "OP-" + System.currentTimeMillis(),
                "WMS 2.1.0"
        );
    }
}
