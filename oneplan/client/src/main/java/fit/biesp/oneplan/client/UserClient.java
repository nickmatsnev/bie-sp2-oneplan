package fit.biesp.oneplan.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    private final WebClient userWebClient;
    public UserClient(@Value("http://localhost:8086") String baseUrl) {
        userWebClient = WebClient.create(baseUrl);
    }

    public Mono<UserDto> create(UserRegistrationDto newUser) {
        return userWebClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newUser)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Flux<LocationDto> getLocations(LocationDto locationDto) {
        return userWebClient.get()
                .uri("/locations/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve() // request specification finished
                .bodyToFlux(LocationDto.class); // interpret response body as a collection
    }


    public Mono<EventDto> createEvent(EventDto newEvent) {
        return userWebClient.post()
                .uri("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newEvent)
                .retrieve()
                .bodyToMono(EventDto.class);
    }
}
