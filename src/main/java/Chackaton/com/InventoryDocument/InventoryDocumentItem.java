package Chackaton.com.InventoryDocument;

import Chackaton.com.Product.Product;
import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;
import jakarta.persistence.*;

@Entity
@Table(name = "document_items")
public class InventoryDocumentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private InventoryDocument document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id")
    private Shelf shelf; // С какой полки берем/на какую кладем

    @Column(name = "quantity_expected")
    private Integer quantityExpected; // "Кол-во" в документе (то, что ты хочешь редактировать)

    // Это поле для мобильного приложения (ТЗ п.10)
    @Column(name = "quantity_actual")
    private Integer quantityActual; // Фактическое кол-во (после сканирования)

    private Double price; // Цена на момент создания документа

    public InventoryDocument getDocument() {
        return document;
    }

    public void setDocument(InventoryDocument document) {
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantityActual() {
        return quantityActual;
    }

    public void setQuantityActual(Integer quantityActual) {
        this.quantityActual = quantityActual;
    }

    public Integer getQuantityExpected() {
        return quantityExpected;
    }

    public void setQuantityExpected(Integer quantityExpected) {
        this.quantityExpected = quantityExpected;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
}