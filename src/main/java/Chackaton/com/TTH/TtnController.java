package Chackaton.com.TTH;

import Chackaton.com.DTO.TtnDocumentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ttn")
public class TtnController {

    private final TtnService ttnService;

    public TtnController(TtnService ttnService) {
        this.ttnService = ttnService;
    }

    @PostMapping("/generate")
    public ResponseEntity<TtnDocumentDto> generateTtn(
            @RequestParam Long organizationFromId,
            @RequestParam Long organizationToId,
            @RequestParam Long warehouseId) {

        try {
            // Генерируем ID отгрузки
            Long shipmentId = System.currentTimeMillis();

            TtnDocumentDto document = ttnService.generateTtnDocument(
                    shipmentId, organizationFromId, organizationToId, warehouseId);

            return ResponseEntity.ok(document);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<TtnDocumentDto> getTtnDocument(@PathVariable Long shipmentId) {
        try {
            // В реальной системе здесь будет логика получения существующего документа
            // Пока возвращаем заглушку
            TtnDocumentDto document = ttnService.generateTtnDocument(
                    shipmentId, 1L, 2L, 1L); // Заглушки для ID организаций и склада

            return ResponseEntity.ok(document);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}