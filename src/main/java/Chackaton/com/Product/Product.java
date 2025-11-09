package Chackaton.com.Product;

import Chackaton.com.Organization.Organization;
import Chackaton.com.Warehouse.Stock.StockItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import Chackaton.com.Image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quantity;
    private String category;
    private String brand;
    private Double price;
    private String articleNumber;
    private String description;
    private String manufacturer;
    private String carBrand;
    private Boolean inStock;

    private Double weight;           // Вес в кг
    private Double length;           // Длина в см
    private Double width;            // Ширина в см
    private Double height;           // Высота в см
    private Double volume;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<StockItem> stockItems = new ArrayList<>();

    public Product(String articleNumber,
                   String brand,
                   String category,
                   String description,
                   Long id,
                   Boolean inStock,
                   String name,
                   String manufacturer,
                   String carBrand,
                   Double price,
                   Integer quantity,
                   Double weight,
                   Double length,
                   Double width,
                   Double height,
                   Double volume) {
        this.articleNumber = articleNumber;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.id = id;
        this.inStock = inStock;
        this.name = name;
        this.manufacturer = manufacturer;
        this.carBrand = carBrand;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.width = width;
        this.volume = volume;
    }

    public Product() {
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
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

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")

    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDate dateOfCreated;


    @PrePersist
    private void init() {
        dateOfCreated = LocalDate.now();
    }

    @JsonIgnore
    public Image getPreviewImage() {
        if (previewImageId == null) return null;
        return images.stream()
                .filter(img -> img.getId().equals(previewImageId))
                .findFirst()
                .orElse(null);
    }

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }

    @JsonIgnore
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }


    public Long getPreviewImageId() {
        return previewImageId;
    }


    // Метод для получения общего количества на всех складах
    public Integer getTotalQuantityInOrganization(Organization organization) {
        if (stockItems == null) return 0;
        return stockItems.stream()
                .filter(item -> item.getOrganization().equals(organization))
                .mapToInt(StockItem::getQuantity)
                .sum();
    }

    // Метод для получения местоположения товара
    public List<String> getProductLocations(Organization organization) {
        if (stockItems == null) return new ArrayList<>();
        return stockItems.stream()
                .filter(item -> item.getOrganization().equals(organization) && item.getQuantity() > 0)
                .map(item -> String.format("Склад: %s, Зона: %s, Стеллаж: %s, Полка: %s",
                        item.getShelf().getRack().getZone().getWarehouse().getName(),
                        item.getShelf().getRack().getZone().getName(),
                        item.getShelf().getRack().getCode(),
                        item.getShelf().getCode()))
                .collect(Collectors.toList());
    }
}
