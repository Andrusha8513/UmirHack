package Chackaton.com.InventoryDocument;


public enum DocumentStatus {
    DRAFT,     // Черновик (можно редактировать)
    COMPLETED, // Проведен (синхронизирован с БД, нередактируемый)
    CANCELLED  // Отменен
}
