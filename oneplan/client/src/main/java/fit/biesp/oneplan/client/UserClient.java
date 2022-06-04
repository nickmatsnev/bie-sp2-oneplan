package fit.biesp.oneplan.client;
import fit.biesp.oneplan.client.models.EventModel;
import fit.biesp.oneplan.client.models.LocationModel;
import fit.biesp.oneplan.client.models.LoginModel;
import fit.biesp.oneplan.client.models.UserRegistrationModel;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    private final WebClient userWebClient;
    public UserClient(@Value("http://localhost:8085") String baseUrl) {
        userWebClient = WebClient.create(baseUrl);
    }


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

    public Mono<String> createLocation(LocationModel newLocation) {
        return userWebClient.post()
                .uri("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newLocation)
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

    public Flux<EventModel> getUserEvents(String nickname) {
        return userWebClient.get()
                .uri("/users/{id}/events", nickname)
                .retrieve() // request specification finished
                .bodyToFlux(EventModel.class); // interpret response body as a collection
    }

    public Mono<LocationModel> getOneLocation(Long newid) {
        return userWebClient.get()
                .uri("/locations/{id}", newid)
                .retrieve() // request specification finished
                .bodyToMono(LocationModel.class);
    }

    public Mono<EventModel> getOneEvent(Long newid) {
        return userWebClient.get()
                .uri("/events/{id}", newid)
                .retrieve() // request specification finished
                .bodyToMono(EventModel.class);
    }


    public Mono<String> createEvent(EventModel newEvent) {
        return userWebClient.post()
                .uri("/events")
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newEvent)
                .retrieve()
                .bodyToMono(String.class);
    }
}
