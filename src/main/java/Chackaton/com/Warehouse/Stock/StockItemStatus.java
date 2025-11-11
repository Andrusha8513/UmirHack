package Chackaton.com.Warehouse.Stock;


public enum StockItemStatus {
    AVAILABLE,      // Доступен для отгрузки
    IN_SORTING,     // Прибыл, на полу, ждет размещения (то, что ты назвал "прибыл на склад")
    RESERVED,       // Зарезервирован для отгрузки
    DEFECTIVE,      // Брак (то, что ты назвал "товар бракованный")
    INVENTORY,       // В процессе инвентаризации (заблокирован)
    SHIPPED         //ОТГРУЖЕН
}