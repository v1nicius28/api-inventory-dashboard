package dashboard.service;


import dashboard.model.User;
import dashboard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService service;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_deveSalvarComSenhaCriptografada() {
        User user = new User();
        user.setName("Maria");
        user.setEmail("maria@gmail.com");
        user.setPassword("123");

        when(passwordEncoder.encode("123")).thenReturn("encoded123");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User salvo = service.registerUser(user);

        assertEquals("Maria", salvo.getName());
        assertEquals("encoded123", salvo.getPassword());
    }
}

