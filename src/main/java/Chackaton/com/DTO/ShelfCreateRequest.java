package Chackaton.com.DTO;

import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.ShelfStatus;

import java.util.Set;

public class ShelfCreateRequest {
    private Integer level;
    private Integer position;
    private Double maxWeight;
    private Double volume;
    private Set<ShelfStatus> statuses;

    // Конструкторы
    public ShelfCreateRequest() {}

    public ShelfCreateRequest(Integer level, Integer position, Double maxWeight, Double volume, Set<ShelfStatus> statuses) {
        this.level = level;
        this.position = position;
        this.maxWeight = maxWeight;
        this.volume = volume;
        this.statuses = statuses;
    }

    // Геттеры и сеттеры
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public Double getMaxWeight() { return maxWeight; }
    public void setMaxWeight(Double maxWeight) { this.maxWeight = maxWeight; }

    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }

    public Set<ShelfStatus> getStatuses() { return statuses; }
    public void setStatuses(Set<ShelfStatus> statuses) { this.statuses = statuses; }
}