package dashboard.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashboard.TestConfig;
import dashboard.dto.request.ProductRequest;
import dashboard.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    ProductRepository repository;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void criarListarProduto() throws Exception {
        MvcResult loginResult = mvc.perform(post("/auth/guest"))
                .andExpect(status().isOk())
                .andReturn();

        String loginBody = loginResult.getResponse().getContentAsString();
        String token = new ObjectMapper().readTree(loginBody).get("token").asText();

        ProductRequest req = new ProductRequest("Mesa", 100.0, 10, "Móveis", "Ikt");

        mvc.perform(post("/products")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/products")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}
