package Chackaton.com.Warehouse;

import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import Chackaton.com.Organization.Organization;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "warehouse" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "organization_id"}),
       // @UniqueConstraint(columnNames = {"access_code", "organization_id"})
} )
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Integer totalCapacity;    // Общая вместимость (в паллетах/единицах)
    private Integer currentCapacity;  // Текущая загруженность
    private Double totalArea;         // Общая площадь (м²)

    @Column(nullable = false)
    private Double maxVolume;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<StorageZone> zones = new ArrayList<>();

//    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
//    private List<Rack> racks = new ArrayList<>();

//    @Enumerated(EnumType.STRING)
//    private StorageType primaryStorageType;

    @ElementCollection(targetClass = StatusWarehouse.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "warehouse_statuses", joinColumns = @JoinColumn(name = "warehouse_id"))
    @Enumerated(EnumType.STRING)
    private Set<StatusWarehouse> statuses;


    public Warehouse(String address,
                     LocalDateTime createdAt,
                     Long id,
                     String name ,
                     Integer totalCapacity ,
                     Double totalArea) {
        this.address = address;
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.totalArea = totalArea;
        this.totalCapacity = totalCapacity;
    }

    public Warehouse() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(Integer currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public Set<StatusWarehouse> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<StatusWarehouse> statuses) {
        this.statuses = statuses;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<StorageZone> getZones() {
        return zones;
    }

    public void setZones(List<StorageZone> zones) {
        this.zones = zones;
    }

    public Double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Double maxVolume) {
        this.maxVolume = maxVolume;
    }
}
