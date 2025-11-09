package Chackaton.com.DTO;

public class MoveProductRequest {
    private Long productId;
    private Long fromShelfId;
    private Long toShelfId;
    private Integer quantity;
    private Long organizationId;


    public Long getFromShelfId() {
        return fromShelfId;
    }

    public void setFromShelfId(Long fromShelfId) {
        this.fromShelfId = fromShelfId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getToShelfId() {
        return toShelfId;
    }

    public void setToShelfId(Long toShelfId) {
        this.toShelfId = toShelfId;
    }
}
