package dashboard.service;

import dashboard.dto.request.ProductRequest;
import dashboard.dto.response.ProductResponse;
import dashboard.exception.ResourceNotFoundException;
import dashboard.mapper.ProductMapper;
import dashboard.model.Product;
import dashboard.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductResponse saveProduct(ProductRequest request) {
        Product product = mapper.toEntity(request);
        Product saved = repository.save(product);
        return mapper.toResponse(saved);
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public Product searchId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));
    }

    public ProductResponse getProductById(String id) {
        Product product = searchId(id);
        return mapper.toResponse(product);
    }

    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = searchId(id);
        mapper.updateEntity(product, request);
        Product updated = repository.save(product);
        return mapper.toResponse(updated);
    }

    public void deleteProduct(String id) {
        searchId(id);
        repository.deleteById(id);
    }
}
