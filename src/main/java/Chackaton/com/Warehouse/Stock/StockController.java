package Chackaton.com.Warehouse.Stock;

import Chackaton.com.DTO.MoveProductRequest;
import Chackaton.com.DTO.StockItemDto;
import Chackaton.com.DTO.StockOperationRequest;
import Chackaton.com.Organization.Organization;
import Chackaton.com.Organization.OrganizationRepository;
import Chackaton.com.Product.Product;
import Chackaton.com.Product.ProductRepository;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.ShelfRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;
    private final ProductRepository productRepository;
    private final ShelfRepository shelfRepository;
    private final OrganizationRepository organizationRepository;

    public StockController(StockService stockService,
                           ProductRepository productRepository,
                           ShelfRepository shelfRepository,
                           OrganizationRepository organizationRepository) {
        this.stockService = stockService;
        this.productRepository = productRepository;
        this.shelfRepository = shelfRepository;
        this.organizationRepository = organizationRepository;
    }

    // Добавить товар на полку збс
    @PostMapping("/add")
    public ResponseEntity<?> addProductToShelf(@RequestBody StockOperationRequest request) {
        try {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            Shelf shelf = shelfRepository.findById(request.getShelfId())
                    .orElseThrow(() -> new RuntimeException("Полка не найдена"));
            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Организация не найдена"));

            StockItem stockItem = stockService.addProductToShelf(
                    product, shelf, request.getQuantity(), organization);

            return ResponseEntity.ok(stockService.toStockItemDto(stockItem));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // Удалить товар с полки
    @PostMapping("/remove-from-shelf")
    public ResponseEntity<?> removeProductFromShelf(@RequestBody StockOperationRequest request) {
        try {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            Shelf shelf = shelfRepository.findById(request.getShelfId())
                    .orElseThrow(() -> new RuntimeException("Полка не найдена"));

            StockItem stockItem = stockService.removeProductFromShelf(
                    product, shelf, request.getQuantity());

            if (stockItem != null) {
                return ResponseEntity.ok(stockService.toStockItemDto(stockItem));
            } else {
                return ResponseEntity.ok("Товар полностью удален с полки");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Переместить товар между полками збс
    @PostMapping("/move")
    public ResponseEntity<?> moveProductBetweenShelves(@RequestBody MoveProductRequest request) {
        try {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            Shelf fromShelf = shelfRepository.findById(request.getFromShelfId())
                    .orElseThrow(() -> new RuntimeException("Исходная полка не найдена"));
            Shelf toShelf = shelfRepository.findById(request.getToShelfId())
                    .orElseThrow(() -> new RuntimeException("Целевая полка не найдена"));
            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Организация не найдена"));

            stockService.moveProductBetweenShelves(
                    product, fromShelf, toShelf, request.getQuantity(), organization);

            return ResponseEntity.ok("Товар успешно перемещен");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Получить все места хранения товара в организации
    @GetMapping("/product-locations")
    public ResponseEntity<List<StockItemDto>> getProductLocations(
            @RequestParam Long productId,
            @RequestParam Long organizationId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            Organization organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new RuntimeException("Организация не найдена"));

            List<StockItem> locations = stockService.findProductLocations(product, organization);
            List<StockItemDto> locationDtos = locations.stream()
                    .map(stockService::toStockItemDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(locationDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Получить общее количество товара в организации
    @GetMapping("/total-quantity")
    public ResponseEntity<Integer> getTotalProductQuantity(
            @RequestParam Long productId,
            @RequestParam Long organizationId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));
            Organization organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new RuntimeException("Организация не найдена"));

            Integer totalQuantity = stockService.getTotalProductQuantity(product, organization);
            return ResponseEntity.ok(totalQuantity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}