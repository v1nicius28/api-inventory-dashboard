package dashboard.controller;

import dashboard.dto.request.ProductRequest;
import dashboard.dto.response.ProductResponse;
import dashboard.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
