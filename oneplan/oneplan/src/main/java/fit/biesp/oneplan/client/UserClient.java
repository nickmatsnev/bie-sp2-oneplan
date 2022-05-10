package fit.biesp.oneplan.client;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.LocationModel;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.model.UserRegistrationModel;
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

    public Mono<UserRegistrationModel> create(UserRegistrationModel newUser) {
        return userWebClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newUser)
                .retrieve()
                .bodyToMono(UserRegistrationModel.class);
    }

    public Flux<LocationModel> getLocations(LocationModel locationDto) {
        return userWebClient.get()
                .uri("/locations/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve() // request specification finished
                .bodyToFlux(LocationModel.class); // interpret response body as a collection
    }


    public Flux<EventModel> createEvent(EventModel newEvent) {
        return userWebClient.post()
                .uri("/events")
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newEvent)
                .retrieve()
                .bodyToFlux(EventModel.class);
    }
}
