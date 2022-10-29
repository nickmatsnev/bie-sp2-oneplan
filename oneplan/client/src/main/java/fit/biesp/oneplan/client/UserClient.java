package fit.biesp.oneplan.client;
import fit.biesp.oneplan.client.models.*;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component /// Class to convert the data from vrowser into api requests to server
public class UserClient {
    private final WebClient userWebClient;
    /// base url of server;
    public UserClient(@Value("http://app-oneplan-221011202557.azurewebsites.net") String baseUrl) {
        userWebClient = WebClient.create(baseUrl);
    }


    public Mono<String> create(UserRegistrationModel newUser) { /// api request builder for creation of a new user
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

    public Mono<String> createLocation(LocationModel newLocation) { /// api request builder for the location creation
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

    public Mono<String> login(LoginModel loginModel){ /// api request builder for logging in
        return userWebClient.post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(loginModel)
                .retrieve()
                .onStatus(
                        HttpStatus.UNAUTHORIZED::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Flux<LocationModel> getLocations() { /// api request builder for getting locations
        return userWebClient.get()
                .uri("/locations/all")
                .retrieve() // request specification finished
                .bodyToFlux(LocationModel.class); // interpret response body as a collection
    }



    public Flux<EventModel> getUserEvents(String nickname) { /// api request builder for getting the events
        return userWebClient.get()
                .uri("/users/{id}/events", nickname)
                .retrieve() // request specification finished
                .bodyToFlux(EventModel.class); // interpret response body as a collection
    }


    public Mono<EventModel> getOneEvent(Long newid) { /// api request builder for getting the event details
        return userWebClient.get()
                .uri("/events/{id}", newid)
                .retrieve() // request specification finished
                .bodyToMono(EventModel.class);
    }


    public Mono<String> createEvent(EventModel newEvent) { /// api request builder for event creation
        return userWebClient.post()
                .uri("/events")
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newEvent)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux<InvitationModel> getInvites() {
        return userWebClient.get()
                .uri("/invitations/all")
                .retrieve()
                .bodyToFlux(InvitationModel.class);
    }

    public Mono<String> createInvite(InvitationDTO newPersonToInvite) { /// В параметр где newPersonToInvite надо вставить что будет передаваться на апи в бэк
        return userWebClient.post()// здесь задаешь метод
                .uri("/invitations/create")// сам АПИ для бэка
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newPersonToInvite) // сюда модельку, можно сделать только с айди ивента и передавать его в бэк с апи а не с bodyvalue
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> accept(int invId, String userId){
        return userWebClient.post()
                .uri("/invitations/{id}/accept", invId)
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", userId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> reject(int invId, String userId){
        return userWebClient.post()
                .uri("/invitations/{id}/reject", invId)
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", userId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
