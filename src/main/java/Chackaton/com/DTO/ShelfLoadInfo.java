package Chackaton.com.DTO;

import Chackaton.com.Warehouse.StorangeZone.Rack.Shelf.Shelf;

public class ShelfLoadInfo {
    private final Shelf shelf;
    private final double totalWeight;
    private final double totalVolume;
    private final int totalItems;
    private final double weightUtilization; // в процентах
    private final double volumeUtilization; // в процентах

    public ShelfLoadInfo(Shelf shelf, double totalWeight, double totalVolume, int totalItems) {
        this.shelf = shelf;
        this.totalWeight = totalWeight;
        this.totalVolume = totalVolume;
        this.totalItems = totalItems;

        this.weightUtilization = shelf.getMaxWeight() != null ?
                (totalWeight / shelf.getMaxWeight()) * 100 : 0;
        this.volumeUtilization = shelf.getVolume() != null ?
                (totalVolume / shelf.getVolume()) * 100 : 0;
    }

    public Shelf getShelf() { return shelf; }
    public double getTotalWeight() { return totalWeight; }
    public double getTotalVolume() { return totalVolume; }
    public int getTotalItems() { return totalItems; }
    public double getWeightUtilization() { return weightUtilization; }
    public double getVolumeUtilization() { return volumeUtilization; }
}