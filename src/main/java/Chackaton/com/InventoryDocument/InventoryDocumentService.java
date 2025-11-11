package Chackaton.com.InventoryDocument;

import Chackaton.com.Organization.OrganizationRepository;
import Chackaton.com.Product.Product;
import Chackaton.com.Warehouse.Stock.StockService;
import Chackaton.com.Warehouse.Stock.StockItem;
import Chackaton.com.Organization.Organization;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InventoryDocumentService {

    private final InventoryDocumentRepository documentRepository;
    private final StockService stockService;
    private final OrganizationRepository organizationRepository;
    private final InventoryDocumentItemRepository inventoryDocumentItemRepository;

    public InventoryDocumentService(InventoryDocumentRepository documentRepository,
                                    StockService stockService,
                                    OrganizationRepository organizationRepository,
                                    InventoryDocumentItemRepository inventoryDocumentItemRepository) {
        this.documentRepository = documentRepository;
        this.stockService = stockService;
        this.organizationRepository = organizationRepository;
        this.inventoryDocumentItemRepository = inventoryDocumentItemRepository;
    }

    // 1. Создание документа (ТТН)
    @Transactional
    public InventoryDocument createDocument(InventoryDocument documentData) {
        documentData.setStatus(DocumentStatus.DRAFT);
        documentData.setCreatedAt(LocalDateTime.now());
        return documentRepository.save(documentData);
    }

    // 2. Редактирование (ТЗ п.10)
    @Transactional
    public InventoryDocumentItem updateDocumentItemQuantity(Long itemId, int actualQuantity) {
        InventoryDocumentItem item = inventoryDocumentItemRepository.findById(itemId).orElseThrow();

        if (item.getDocument().getStatus() != DocumentStatus.DRAFT) {
            throw new RuntimeException("Нельзя редактировать проведенный документ!");
        }

        item.setQuantityActual(actualQuantity);
        return inventoryDocumentItemRepository.save(item);
    }

    // 3. СИНХРОНИЗАЦИЯ (ТЗ п.11) - ОБНОВЛЕННАЯ ВЕРСИЯ
    @Transactional
    public InventoryDocument completeDocument(Long documentId) {
        InventoryDocument doc = documentRepository.findById(documentId).orElseThrow();

        if (doc.getStatus() != DocumentStatus.DRAFT) {
            throw new RuntimeException("Документ уже проведен.");
        }

        Organization org = doc.getOrganization();

        // **ОБНОВЛЕННАЯ ЛОГИКА**
        for (InventoryDocumentItem item : doc.getItems()) {

            Product product = item.getProduct();
            Shelf shelf = item.getShelf();

            // --- ЛОГИКА ДЛЯ ТТН (ОТГРУЗКА) ---
            if (doc.getType() == DocumentType.OUTGOING) {
                Integer quantity = item.getQuantityExpected(); // У ТТН нет "факта"
                stockService.removeProductFromShelf(product, shelf, quantity);
            }

            // --- ЛОГИКА ДЛЯ ПОСТУПЛЕНИЯ ---
            else if (doc.getType() == DocumentType.INCOMING) {
                Integer quantity = item.getQuantityExpected();
                stockService.addProductToShelf(product, shelf, quantity, org);
            }

            // --- **НОВАЯ ЛОГИКА ДЛЯ ИНВЕНТАРИЗАЦИИ** ---
            else if (doc.getType() == DocumentType.INVENTORY) {

                // Если "Факт" не был заполнен, пропускаем строку
                if (item.getQuantityActual() == null) {
                    continue;
                }

                Integer expected = item.getQuantityExpected(); // План
                Integer actual = item.getQuantityActual();     // Факт

                int delta = actual - expected; // Разница

                if (delta > 0) {
                    // Нашли "Излишек". Добавляем на полку.
                    stockService.addProductToShelf(product, shelf, delta, org);
                }
                else if (delta < 0) {
                    // Нашли "Недостачу". Списываем с полки.
                    // Math.abs() т.к. removeProduct ожидает положительное число
                    stockService.removeProductFromShelf(product, shelf, Math.abs(delta));
                }
                // Если delta == 0, ничего делать не нужно, остатки верны.
            }
        }

        doc.setStatus(DocumentStatus.COMPLETED);
        doc.setCompletedAt(LocalDateTime.now());
        return documentRepository.save(doc);
    }
}