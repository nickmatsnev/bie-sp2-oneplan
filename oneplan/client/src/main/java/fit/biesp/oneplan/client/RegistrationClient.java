package fit.biesp.oneplan.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RegistrationClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient userWebClient;

    public RegistrationClient(@Value("${backend.url}") String baseUrl) {
        userWebClient = WebClient.create(baseUrl + "/users");
    }

    public Mono<UserDto> create(UserDto newUser) {
        return userWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newUser)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}
