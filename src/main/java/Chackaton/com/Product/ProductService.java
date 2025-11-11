package Chackaton.com.Product;

import Chackaton.com.Organization.Organization;
import Chackaton.com.Warehouse.Stock.StockItem;
import Chackaton.com.Warehouse.Stock.StockItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import Chackaton.com.DTO.ImageDto;
import Chackaton.com.DTO.ProductDto;
import Chackaton.com.Image.Image;
import Chackaton.com.Image.ImageRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;








@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final StockItemRepository stockItemRepository;

    public ProductService(ProductRepository productRepository,
                          ImageRepository imageRepository,
                          StockItemRepository stockItemRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.stockItemRepository = stockItemRepository;
    }


    public List<Product> getAllProduct(String name) {
        // List<Product> products = productRepository.findAll();
        if (name != null)
            return productRepository.findByName(name);
        return productRepository.findAll();
    }


    @Transactional
   // @CacheEvict(value = "products", allEntries = true)
    public Product createProduct(Product product, List<MultipartFile> files) throws IOException {
        if (files != null && !files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (file.isEmpty()) {
                    continue;
                }
                Image image = toImageEntity(file);
                if (i == 0) {
                    image.setPreviewImage(true);
                }

                product.addImageToProduct(image);
            }
        }
        if (productRepository.findByArticleNumber(product.getArticleNumber()).isPresent()) {
            throw new IllegalArgumentException("Продукт с таким артикулом уже существует");
        }
        Product saveProduct = productRepository.save(product);
        if (!saveProduct.getImages().isEmpty()) {
            saveProduct.setPreviewImageId(saveProduct.getImages().get(0).getId());
        }
        return productRepository.save(saveProduct);

    }


    private Image toImageEntity(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        Image image = new Image();
        image.setName(UUID.randomUUID().toString());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Файл должен быть изображением");
        }

        if (file.getSize() > 50_000_000) {
            throw new IllegalArgumentException("Размер файла не должен превышать 50MB");
        }

        image.setBytes(file.getBytes());
        return image;
    }


    @Transactional
   // @CacheEvict(value = "products", key = "#id")
    public Product updateProduct(Long id,
                                 Product productDetails,
                                 List<MultipartFile> files) throws IOException {
        Product product = findById(id);

        product.setName(productDetails.getName());
       // product.setQuantity(productDetails.getQuantity());
        product.setCategory(productDetails.getCategory());
        product.setBrand(productDetails.getBrand());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        product.setCarBrand(productDetails.getCarBrand());
        product.setInStock(productDetails.getInStock());
        product.setManufacturer(productDetails.getManufacturer());

        if (!product.getArticleNumber().equals(productDetails.getArticleNumber())) {
            if (productRepository.findByArticleNumber(productDetails.getArticleNumber()).isPresent()) {
                throw new IllegalArgumentException("Продукт с таким артикулом существует");
            }
            product.setArticleNumber(productDetails.getArticleNumber());
        }

        if (files != null && !files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                Image image = toImageEntity(file);
                product.addImageToProduct(image);
            }
        }

        if (product.getPreviewImage() == null && !product.getImages().isEmpty()) {
            product.setPreviewImageId(product.getImages().get(0).getId());
            product.getImages().get(0).setPreviewImage(true);
        }
        return productRepository.save(product);
    }



    @Transactional
    public void setProductPreviewImage(Long productId, Long imageId) {
        Product product = findById(productId);
        Image newPreview = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("изображения с id= " + imageId + "не найдено"));
        if (!newPreview.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Изображение с id= " + imageId + "не принадлежит продукту");
        }
        product.getImages().forEach(img -> img.setPreviewImage(false));

        newPreview.setPreviewImage(true);
        product.setPreviewImageId(newPreview.getId());

        imageRepository.saveAll(product.getImages());
        productRepository.save(product);
    }

    @Transactional
    public void deleteImageFromProduct(Long productId, Long imageId) {
        Product product = findById(productId);
        Image imageToDelete = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Изображения с таким id= " + imageId + "нет"));
        if (!imageToDelete.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Изображение с id=" + imageId + " не принадлежит продукту с id=" + productId + ".");
        }

        product.getImages().remove(imageToDelete);

        if (imageToDelete.getId().equals(product.getPreviewImageId())) {
            if (!product.getImages().isEmpty()) {
                Image newPreview = product.getImages().get(0);
                newPreview.setPreviewImage(true);
                product.setPreviewImageId(newPreview.getId());


            } else {
                product.setPreviewImageId(null);
            }
        }
        imageRepository.delete(imageToDelete);
        productRepository.save(product);

    }



    @Transactional
   // @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Продукт с id=" + id + " не найден.");
        }
        productRepository.deleteById(id);
    }

   // @Cacheable(value = "products", key = "#id")
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продукта с таким id" + id + " нет"));
    }


    @Transactional(readOnly = true)
    public List<ProductDto> getAllProductsAsDto(String name) {
        List<Product> products;
        if (name != null && !name.isEmpty()) {
            products = productRepository.findByName(name);
        } else {
            products = productRepository.findAllWithImages();
        }
        return products.stream()
                .map(this::toProductDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProductDto findByIdAsDto(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продукта с таким id " + id + " нет"));
        return toProductDto(product);
    }

    private ProductDto toProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
       // dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setArticleNumber(product.getArticleNumber());
        dto.setDescription(product.getDescription());
        dto.setManufacturer(product.getManufacturer());
        dto.setCarBrand(product.getCarBrand());
        dto.setInStock(product.getInStock());
        dto.setPreviewImageId(product.getPreviewImageId());
        dto.setWeight(product.getWeight());
        dto.setWidth(product.getWidth());
        dto.setLength(product.getLength());
        dto.setHeight(product.getHeight());
        dto.setVolume(product.getVolume());

        // ВАЖНО:  безопасно обращаюсь к ленивой коллекции ВНУТРИ транзакции
        List<ImageDto> imageDto = product.getImages().stream()
                .map(img -> new ImageDto(img.getId(), img.isPreviewImage(), img.getOriginalFileName()))
                .collect(Collectors.toList());
        dto.setImages(imageDto);

        return dto;
    }
    public Double calculateVolume(Product product) {

        if (product.getLength() != null && product.getWidth() != null && product.getHeight() != null) {
            return (product.getLength() * product.getWidth() * product.getHeight()) / 1000000.0; // из см³ в м³
        }
        return null;
    }


    // Получить общее количество товара в организации
    public Integer getTotalProductQuantityInOrganization(Product product, Organization organization) {
        List<StockItem> stockItems = stockItemRepository.findByProductAndOrganization(product, organization);
        return stockItems.stream()
                .mapToInt(StockItem::getQuantity)
                .sum();
    }

    // Получить местоположение товара в организации
    public List<String> getProductLocations(Product product, Organization organization) {
        List<StockItem> stockItems = stockItemRepository.findByProductAndOrganization(product, organization);

        return stockItems.stream()
                .filter(item -> item.getQuantity() > 0)
                .map(item -> String.format("Склад: %s, Зона: %s, Стеллаж: %s, Полка: %s",
                        item.getShelf().getRack().getZone().getWarehouse().getName(),
                        item.getShelf().getRack().getZone().getName(),
                        item.getShelf().getRack().getCode(),
                        item.getShelf().getCode()))
                .collect(Collectors.toList());
    }

    // Получить детальную информацию о размещении товара
    public List<StockItem> getProductStockDetails(Product product, Organization organization) {
        return stockItemRepository.findByProductAndOrganization(product, organization);
    }

}

