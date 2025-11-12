package Chackaton.com.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class TtnDocumentDto {
    private Long id;
    private String documentNumber;
    private LocalDateTime documentDate;
    private OrganizationDto organizationFrom;
    private OrganizationDto organizationTo;
    private WarehouseDto warehouse;
    private String storageZone;
    private String shipmentStatus;
    private LocalDateTime shipmentDate;
    private List<TtnProductDto> products;
    private TtnTotalsDto totals;
    private TtnPersonnelDto personnel;
    private TtnSystemInfoDto systemInfo;

    // Конструкторы, геттеры и сеттеры
    public TtnDocumentDto() {}

    public TtnDocumentDto(Long id, String documentNumber, LocalDateTime documentDate,
                          OrganizationDto organizationFrom, OrganizationDto organizationTo,
                          WarehouseDto warehouse, String storageZone, String shipmentStatus,
                          LocalDateTime shipmentDate, List<TtnProductDto> products,
                          TtnTotalsDto totals, TtnPersonnelDto personnel, TtnSystemInfoDto systemInfo) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.documentDate = documentDate;
        this.organizationFrom = organizationFrom;
        this.organizationTo = organizationTo;
        this.warehouse = warehouse;
        this.storageZone = storageZone;
        this.shipmentStatus = shipmentStatus;
        this.shipmentDate = shipmentDate;
        this.products = products;
        this.totals = totals;
        this.personnel = personnel;
        this.systemInfo = systemInfo;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrganizationDto getOrganizationFrom() {
        return organizationFrom;
    }

    public void setOrganizationFrom(OrganizationDto organizationFrom) {
        this.organizationFrom = organizationFrom;
    }

    public OrganizationDto getOrganizationTo() {
        return organizationTo;
    }

    public void setOrganizationTo(OrganizationDto organizationTo) {
        this.organizationTo = organizationTo;
    }

    public TtnPersonnelDto getPersonnel() {
        return personnel;
    }

    public void setPersonnel(TtnPersonnelDto personnel) {
        this.personnel = personnel;
    }

    public List<TtnProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<TtnProductDto> products) {
        this.products = products;
    }

    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getStorageZone() {
        return storageZone;
    }

    public void setStorageZone(String storageZone) {
        this.storageZone = storageZone;
    }

    public TtnSystemInfoDto getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(TtnSystemInfoDto systemInfo) {
        this.systemInfo = systemInfo;
    }

    public TtnTotalsDto getTotals() {
        return totals;
    }

    public void setTotals(TtnTotalsDto totals) {
        this.totals = totals;
    }

    public WarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDto warehouse) {
        this.warehouse = warehouse;
    }
}
