package Chackaton.com.InventoryDocument;

import Chackaton.com.Organization.Organization;
import Chackaton.com.Warehouse.Warehouse;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory_documents")
public class InventoryDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse; // Склад, к которому относится документ

    @Enumerated(EnumType.STRING)
    private DocumentType type; // ENUM: INCOMING, OUTGOING (ТТН), INTERNAL_MOVE, INVENTORY

    @Enumerated(EnumType.STRING)
    private DocumentStatus status; // ENUM: DRAFT (Черновик), COMPLETED (Проведен), CANCELLED

    private String shipper; // Грузоотправитель (просто строкой для хакатона)
    private String consignee; // Грузополучатель

    private LocalDateTime createdAt;
    private LocalDateTime completedAt; // Дата "Проведения" (синхронизации)

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryDocumentItem> items = new ArrayList<>();

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InventoryDocumentItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryDocumentItem> items) {
        this.items = items;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}




