package dashboard.validation;

import dashboard.dto.request.ProductRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void nomeObrigatorio() {
        ProductRequest req = new ProductRequest(null, 100.0, 20, "Móveis", "Ikt");
        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void nomeNaoPodeSerVazio() {
        ProductRequest req = new ProductRequest("", 100.0, 20, "Móveis", "Ikt");
        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void precoObrigatorio() {
        ProductRequest req = new ProductRequest("Mesa", null, 20, "Móveis", "Ikt");
        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void precoDeveSerPositivo() {
        ProductRequest req = new ProductRequest("Mesa", -10.0, 20, "Móveis", "Ikt");
        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void quantidadeObrigatoria() {
        ProductRequest req = new ProductRequest("Mesa", 100.0, null, "Móveis", "Ikt");
        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void categoriaObrigatoria() {
        ProductRequest req = new ProductRequest("Mesa", 100.0, 20, null, "Ikt");
        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void fabricanteObrigatorio() {
        ProductRequest req = new ProductRequest("Mesa", 100.0, 20, "Móveis", null);
        var violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }
}
