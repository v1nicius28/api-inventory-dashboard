package dashboard.config;

import dashboard.model.User;
import dashboard.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GuestUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GuestUserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String @NonNull ... args) {
        String guestEmail = "guest@sistema.com";

        boolean alreadyExists = userRepository.findUserByEmail(guestEmail).isPresent();

        if (!alreadyExists) {
            User guest = new User();
            guest.setName("Convidado");
            guest.setEmail(guestEmail);
            guest.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            guest.setRole("GUEST");

            userRepository.save(guest);
        }
    }
}
