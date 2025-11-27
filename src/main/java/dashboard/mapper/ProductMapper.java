package dashboard.mapper;

import dashboard.dto.request.ProductRequest;
import dashboard.dto.response.ProductResponse;
import dashboard.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .category(request.category())
                .brand(request.brand())
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getBrand()
        );
    }

    public void updateEntity(Product product, @Valid ProductRequest request) {
        if (request.name() != null) product.setName(request.name());
        if (request.price() != null) product.setPrice(request.price());
        if (request.quantity() != null) product.setQuantity(request.quantity());
        if (request.category() != null) product.setCategory(request.category());
        if (request.brand() != null) product.setBrand(request.brand());
    }
}
