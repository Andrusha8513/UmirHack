package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "shelves" ,uniqueConstraints = {
        @UniqueConstraint(columnNames = {"rack_id", "code"}) // уникальность в рамках стеллажа
})
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code; // "A-01-01", "B-12-03" - уникальный код


    private Integer level;     // Уровень (1, 2, 3...) ебалл я эти уровни  парни
    private Integer position;  // Позиция на уровне


    private Double maxWeight;
    private Double volume;





    @ElementCollection(targetClass = ShelfStatus.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "shelf_statuses", joinColumns = @JoinColumn(name = "shelf_id"))
    @Enumerated(EnumType.STRING)
    private Set<ShelfStatus> statuses;


    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_id" , nullable = false)
    private Rack rack;


    public Shelf(String code,
                 LocalDateTime createdAt,
                 Long id,
                 Integer level,
                 Double maxWeight,
                 Integer position,
                 Set<ShelfStatus> statuses,
                 Double volume) {
        this.code = code;
        this.createdAt = createdAt;
        this.id = id;
        this.level = level;
        this.maxWeight = maxWeight;
        this.position = position;
        this.statuses = statuses;
        this.volume = volume;
    }

    public Shelf() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }


    public Set<ShelfStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<ShelfStatus> statuses) {
        this.statuses = statuses;
    }

    public boolean hasStatus(ShelfStatus status) {
        return statuses != null && statuses.contains(status);
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}