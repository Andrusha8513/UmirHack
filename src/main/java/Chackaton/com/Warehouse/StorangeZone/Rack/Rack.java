package Chackaton.com.Warehouse.StorangeZone.Rack;

import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;
import Chackaton.com.Warehouse.StorangeZone.StorageZone;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rack" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"zone_id", "code"}) // ← исправить на zone_id
})

public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code; // "A-01", "B-12" - уникальный код как в озоне крч
    private String name;





    private Integer totalLevels;   // Количество уровней/полок , нахуй я это решали делать
    private Double maxWeight;      // Макс. нагрузка (кг)
    private Double height;         // Высота (м)
    private Double width;          // Ширина (м)
    private Double depth;          // Глубина (м)


    @ElementCollection(targetClass = RackType.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "rack_type", joinColumns = @JoinColumn(name = "rack_id"))
    @Enumerated(EnumType.STRING)
    private Set<RackType> types;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id" , nullable = false)
    private StorageZone zone;

    @OneToMany(mappedBy = "rack")
    private List<Shelf> shelves = new ArrayList<>();

    private LocalDateTime createdAt;

    public Rack(String code,
                LocalDateTime createdAt,
                Double depth,
                Double height,
                Long id,
                Double maxWeight,
                String name,
                Integer totalLevels,
                Double width) {
        this.code = code;
        this.createdAt = createdAt;
        this.depth = depth;
        this.height = height;
        this.id = id;
        this.maxWeight = maxWeight;
        this.name = name;
        this.totalLevels = totalLevels;
        this.width = width;
    }

    public Rack() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }

    public Integer getTotalLevels() {
        return totalLevels;
    }

    public void setTotalLevels(Integer totalLevels) {
        this.totalLevels = totalLevels;
    }

    public Set<RackType> getTypes() {
        return types;
    }

    public void setTypes(Set<RackType> types) {
        this.types = types;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public StorageZone getZone() {
        return zone;
    }

    public void setZone(StorageZone zone) {
        this.zone = zone;
    }
}
