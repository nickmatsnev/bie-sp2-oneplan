package fit.biesp.oneplan.client;
import fit.biesp.oneplan.client.exception.UserAlreadyExistsException;
import fit.biesp.oneplan.client.models.EventModel;
import fit.biesp.oneplan.client.models.LocationModel;
import fit.biesp.oneplan.client.models.LoginModel;
import fit.biesp.oneplan.client.models.UserRegistrationModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    private final WebClient userWebClient;
    public UserClient(@Value("http://localhost:8085") String baseUrl) {
        userWebClient = WebClient.create(baseUrl);
    }

    String userExists = "user already exists!";

    public Mono<String> create(UserRegistrationModel newUser) {
            return userWebClient.post()
                    .uri("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(newUser)
                    .retrieve()
                    .onStatus(
                            HttpStatus.BAD_REQUEST::equals,
                            response -> response.bodyToMono(String.class).map(Exception::new)
                    )
                    .bodyToMono(String.class);
    }

    public Mono<String> login(LoginModel loginModel){
        return userWebClient.post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(loginModel)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);

    }

    public Flux<LocationModel> getLocations() {
        return userWebClient.get()
                .uri("/locations/all")
                .retrieve() // request specification finished
                .bodyToFlux(LocationModel.class); // interpret response body as a collection
    }

    public Mono<LocationModel> getOneLocation(Long id) {
        return userWebClient.get()
                .uri("/locations/{id}")
                .retrieve() // request specification finished
                .bodyToMono(LocationModel.class); // interpret response body as a collection
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
