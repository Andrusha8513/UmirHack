package Chackaton.com.DTO;

public class TtnLocationDto {
    private String warehouse;
    private String zone;
    private String rack;
    private String shelf;

    public TtnLocationDto(String name, String name1, String code, String code1) {
    }


    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
