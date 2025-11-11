package Chackaton.com.Warehouse.Stock;

import Chackaton.com.Organization.Organization;
import Chackaton.com.Product.Product;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;
import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import Chackaton.com.Warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {

    // Найти все остатки товара в организации
    List<StockItem> findByProductAndOrganization(Product product, Organization organization);

    // Найти остаток конкретного товара на конкретной полке
    Optional<StockItem> findByProductAndShelf(Product product, Shelf shelf);

    // Найти все товары на полке
    List<StockItem> findByShelf(Shelf shelf);

    // Найти все товары в зоне хранения
    @Query("SELECT si FROM StockItem si WHERE si.shelf.rack.zone = :zone AND si.organization = :organization")
    List<StockItem> findByZoneAndOrganization(@Param("zone") StorageZone zone, @Param("organization") Organization organization);

    // Найти все товары на складе
    @Query("SELECT si FROM StockItem si WHERE si.shelf.rack.zone.warehouse = :warehouse AND si.organization = :organization")
    List<StockItem> findByWarehouseAndOrganization(@Param("warehouse") Warehouse warehouse, @Param("organization") Organization organization);

//    // Поиск по штрих-коду товара в организации
//    @Query("SELECT si FROM StockItem si WHERE si.product.barcode = :barcode AND si.organization = :organization")
//    List<StockItem> findByProductBarcodeAndOrganization(@Param("barcode") String barcode, @Param("organization") Organization organization);

    // Проверить существование
    boolean existsByProductAndShelf(Product product, Shelf shelf);

    @Query("SELECT SUM(si.quantity * si.product.volume) " +
            "FROM StockItem si " +
            "WHERE si.shelf.rack.zone.warehouse = :warehouse")
    //Эта JPQL-query — ключ. Она идет по всем StockItem на складе, умножает кол-во на объем продукта и суммирует

    Double getTotalVolumeInWarehouse(@Param("warehouse") Warehouse warehouse);
}