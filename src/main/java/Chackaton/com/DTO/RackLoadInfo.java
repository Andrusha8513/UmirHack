package Chackaton.com.DTO;

import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;

import java.util.Map;

public class RackLoadInfo {
    private final Rack rack;
    private final double totalWeight;
    private final double totalVolume;
    private final int totalItems;
    private final double weightUtilization; // в процентах
    private final double volumeUtilization; // в процентах
    private final Map<Long, ShelfLoadInfo> shelfLoads;

    public RackLoadInfo(Rack rack, double totalWeight, double totalVolume, int totalItems, Map<Long, ShelfLoadInfo> shelfLoads) {
        this.rack = rack;
        this.totalWeight = totalWeight;
        this.totalVolume = totalVolume;
        this.totalItems = totalItems;
        this.shelfLoads = shelfLoads;

        // Рассчитываем утилизацию
        this.weightUtilization = rack.getMaxWeight() != null ?
                (totalWeight / rack.getMaxWeight()) * 100 : 0;
        this.volumeUtilization = calculateRackVolume(rack) != null ?
                (totalVolume / calculateRackVolume(rack)) * 100 : 0;
    }

    private Double calculateRackVolume(Rack rack) {
        if (rack.getHeight() != null && rack.getWidth() != null && rack.getDepth() != null) {
            return rack.getHeight() * rack.getWidth() * rack.getDepth();
        }
        return null;
    }

    // Геттеры
    public Rack getRack() { return rack; }
    public double getTotalWeight() { return totalWeight; }
    public double getTotalVolume() { return totalVolume; }
    public int getTotalItems() { return totalItems; }
    public double getWeightUtilization() { return weightUtilization; }
    public double getVolumeUtilization() { return volumeUtilization; }
    public Map<Long, ShelfLoadInfo> getShelfLoads() { return shelfLoads; }
}