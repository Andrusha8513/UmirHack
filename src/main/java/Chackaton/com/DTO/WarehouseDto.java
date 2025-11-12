package Chackaton.com.DTO;

public class WarehouseDto {
    private Long id;
    private String name;
    private String address;

    public WarehouseDto(Long id, String name, String address) {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
