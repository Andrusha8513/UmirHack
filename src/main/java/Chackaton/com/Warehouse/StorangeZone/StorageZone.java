package Chackaton.com.Warehouse.StorangeZone;

import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import Chackaton.com.Warehouse.Warehouse;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "storageZone")
public class StorageZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ElementCollection(targetClass = StorageType.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "storage_zone_types", joinColumns = @JoinColumn(name = "storage_zone_id"))
    @Enumerated(EnumType.STRING)
    private Set<StorageType> storageTypes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "zone")
    private List<Rack> racks = new ArrayList<>();

    private LocalDateTime createdAt;

    public StorageZone(LocalDateTime createdAt,
                       String description,
                       Long id,
                       String name) {
        this.createdAt = createdAt;
        this.description = description;
        this.id = id;
        this.name = name;
    }

    public StorageZone() {
    }

    public List<Rack> getRacks() {
        return racks;
    }

    public void setRacks(List<Rack> racks) {
        this.racks = racks;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }
}
