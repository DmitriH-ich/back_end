package de.ait_tr.g_36.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


@Schema(description = "Class that describes Product") // add for Swagger

public class ProductDto {

    @Schema( // add for Swagger
            description = "Product unique identifier",
            example = "111",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(description = "Product title", example = "banana") // add for Swagger
    private String title;

    @Schema(description = "Product price", example = "190.00") // add for Swagger
    private BigDecimal price;

    //private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }

/*    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %d",
                id, title, price );
    }*/
@Override
public String toString() {
    return String.format("ProductDto{id=%s, title='%s', price=%s}",
            id != null ? id : "null",
            title != null ? title : "null",
            price != null ? String.format("%.2f", price) : "null");
}
}
