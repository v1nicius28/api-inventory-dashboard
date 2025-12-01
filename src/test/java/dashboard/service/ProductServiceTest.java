package dashboard.service;
import dashboard.dto.request.ProductRequest;
import dashboard.dto.response.ProductResponse;
import dashboard.exception.ResourceNotFoundException;
import dashboard.mapper.ProductMapper;
import dashboard.model.Product;
import dashboard.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper; // <- mock do mapper

    @InjectMocks
    private ProductService service;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarProduto_quandoValido_deveRetornarProdutoSalvo() {
        // Arrange
        ProductRequest req = new ProductRequest("Mesa", 100.0, 10, "Móveis", "Ikea");

        Product p = Product.builder()
                .id("123")
                .name("Mesa")
                .price(100.0)
                .quantity(10)
                .category("Móveis")
                .brand("Ikea")
                .build();

        ProductResponse resExpected = new ProductResponse(
                p.getId(), p.getName(), p.getPrice(), p.getQuantity(), p.getCategory(), p.getBrand()
        );

        when(mapper.toEntity(req)).thenReturn(p);
        when(mapper.toResponse(p)).thenReturn(resExpected);

        when(repository.save(any(Product.class))).thenReturn(p);

        ProductResponse res = service.saveProduct(req);

        assertEquals("Mesa", res.name());
        assertEquals(100.0, res.price());
        assertEquals(10, res.quantity());
        assertEquals("Móveis", res.category());
        assertEquals("Ikea", res.brand());
    }

    @Test
    void getProductById_quandoProdutoNaoExiste_deveLancarResourceNotFoundException() {
        // Arrange
        String id = "123";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getProductById(id));

        assertEquals("Produto não encontrado com o id: 123", ex.getMessage());
    }
}
