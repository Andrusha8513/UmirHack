package Chackaton.com.DTO;

public class TtnProductDto {
    private Long id;
    private String articleNumber;
    private String name;
    private String description;
    private String manufacturer;
    private Integer quantity;
    private Double price;
    private Double total;
    private Double weight;
    private Double volume;
    private TtnLocationDto location;
    private String status;
    private String imageUrl;

    public TtnProductDto(String articleNumber, String description, Long id, String imageUrl, TtnLocationDto location, String manufacturer, String name, Double price, Integer quantity, String status, Double total, Double volume, Double weight) {
        this.articleNumber = articleNumber;
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.location = location;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.total = total;
        this.volume = volume;
        this.weight = weight;
    }

    public TtnProductDto() {
    }

    public TtnProductDto(Long id, String articleNumber, String name, String description, String manufacturer, Integer quantity, Double price, double price1, Double weight, Double volume, TtnLocationDto locationDto, String name1, String s) {
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TtnLocationDto getLocation() {
        return location;
    }

    public void setLocation(TtnLocationDto location) {
        this.location = location;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}