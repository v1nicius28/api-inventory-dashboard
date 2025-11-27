package dashboard.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dashboard.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    private String secret = "secret";

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", user.getId())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validadeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asString())
                    .email(decode.getSubject())
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
