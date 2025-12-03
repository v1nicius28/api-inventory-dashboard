package dashboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashboard.config.TokenConfig;
import dashboard.dto.request.RegisterUserRequest;
import dashboard.model.User;
import dashboard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean private TokenConfig tokenConfig;

    @Test
    void registrarUsuario_deveRetornar201() throws Exception {
        RegisterUserRequest req = new RegisterUserRequest("João", "joao@gmail.com", "123");
        User saved = new User();
        saved.setName("João");
        saved.setEmail("joao@gmail.com");

        when(passwordEncoder.encode("123")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(saved);

        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("João"));
    }

    @Test
    void loginUsuario_deveRetornarToken200() throws Exception {

        class LoginRequest {
            public String email;
            public String password;
            public LoginRequest(String email, String password) {
                this.email = email; this.password = password;
            }
            public String email() { return email; }
            public String password() { return password; }
        }

        LoginRequest req = new LoginRequest("joao@gmail.com", "123");
        String tokenEsperado = "mocked-jwt-token-xyz789";


        User authenticatedUser = new User();
        authenticatedUser.setEmail(req.email());
        authenticatedUser.setPassword("encoded_pass");

        Authentication successfulAuth = new UsernamePasswordAuthenticationToken(
                authenticatedUser,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(successfulAuth);

        when(tokenConfig.generateToken(authenticatedUser))
                .thenReturn(tokenEsperado);

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(tokenEsperado));
    }

    @Test
    void loginUsuario_deveRetornar401SeCredenciaisInvalidas() throws Exception {
        class LoginRequest {
            public String email;
            public String password;
            public LoginRequest(String email, String password) {
                this.email = email; this.password = password;
            }
        }

        LoginRequest req = new LoginRequest("joao@gmail.com", "senha-errada");

        doThrow(new BadCredentialsException("Credenciais Inválidas"))
                .when(authenticationManager)
                .authenticate(any(Authentication.class));

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }
}
