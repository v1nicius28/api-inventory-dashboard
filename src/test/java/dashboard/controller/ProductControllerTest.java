package dashboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashboard.config.TokenConfig;
import dashboard.dto.request.ProductRequest;
import dashboard.dto.response.ProductResponse;
import dashboard.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @MockitoBean
    private ProductService service;

    @MockitoBean
    private TokenConfig tokenConfig;

    @Test
    void listarProdutos_deveRetornarLista() throws Exception {
        when(service.getAllProducts())
                .thenReturn(List.of(
                        new ProductResponse("1", "Mesa", 200.0, 5, "Móveis", "Ikt")
                ));

        mvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Mesa"));
    }

    @Test
    void criarProduto_deveRetornar201() throws Exception {
        ProductRequest req = new ProductRequest("Cadeira", 150.0, 10, "Móveis", "Ikt");
        ProductResponse res = new ProductResponse("1", "Cadeira", 150.0, 10, "Móveis", "Ikt");

        when(service.saveProduct(any())).thenReturn(res);

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Cadeira"));
    }
}





