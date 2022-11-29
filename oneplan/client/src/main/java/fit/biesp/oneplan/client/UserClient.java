package fit.biesp.oneplan.client;
import fit.biesp.oneplan.client.exception.UserNotFoundException;
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

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Component /// Class to convert the data from vrowser into api requests to server
public class UserClient {
    private final WebClient userWebClient;
    /// base url of server;
    //@Value("http://app-oneplan-221011202557.azurewebsites.net/"
    public UserClient(@Value("http://localhost:8085/") String baseUrl) {
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
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(UserNotFoundException::new)
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

    public Mono<UserModel> getOneUser(String newid) { /// api request builder for getting the event details
        return userWebClient.get()
                .uri("/users/{id}", newid)
                .retrieve() // request specification finished
                .bodyToMono(UserModel.class);
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

    public Mono<InvitationWelcomeModel> getInvite(long id) {
        return userWebClient.get()
                .uri("/invitations/{id}", id)
                .retrieve()
                .bodyToMono(InvitationWelcomeModel.class);
    }

    public Mono<String> createInvite(InvitationDTO newPersonToInvite) { /// В параметр где newPersonToInvite надо вставить что будет передаваться на апи в бэк
        return userWebClient.post()// здесь задаешь метод
                .uri("/invitations/send")// сам АПИ для бэка
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newPersonToInvite) // сюда модельку, можно сделать только с айди ивента и передавать его в бэк с апи а не с bodyvalue
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux<InvitationWelcomeModel> getUserInvites(String nickname) { /// api request builder for getting the events
        return userWebClient.get()
                .uri("/invitations/users/nickname/{nickname}", nickname)
                .retrieve() // request specification finished
                .bodyToFlux(InvitationWelcomeModel.class); // interpret response body as a collection
    }
    public Flux<FriendModel> getFriendsById(String nickname){
        return userWebClient.get()
                .uri("/friends/user/{nickname}", nickname)
                .retrieve()
                .bodyToFlux(FriendModel.class);
    }

    public Flux<UserModel> getAllUsers(){
        return userWebClient.get()
                .uri("/users/all")
                .retrieve()
                .bodyToFlux(UserModel.class);
    }

    public Mono<String> accept(int invId){
        return userWebClient.post()
                .uri("/invitations/{id}/accept", invId)
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> reject(int invId){
        return userWebClient.post()
                .uri("/invitations/{id}/reject", invId)
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }
    public Mono<String> deleteFriend(int userId, String email){
        return userWebClient.post()
                .uri("/friends/{userid}/{email}", userId, email)
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(email)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);

    }
    public Mono<String> createFriend(FriendCreateModel friendCreateModel) { /// api request builder for event creation
        return userWebClient.post()
                .uri("/friends/create")
                .contentType(MediaType.APPLICATION_JSON) // TEXT_HTML
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(friendCreateModel)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Mono<String> createEventInvite(EventInviteRealModel inviteModel){
        System.out.println("Welcome to createEventInvite!");
        System.out.println(inviteModel.getRecipientEmail());
        return userWebClient.post()
                .uri("/event-invites/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(inviteModel)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }
    public Flux<EventInviteModel> getEventInvitesByRecipientNickname(String nickname){
        return userWebClient.get()
                .uri("/event-invites/nickname/{nickname}", nickname)
                .retrieve()
                .bodyToFlux(EventInviteModel.class);
    }
    public Flux<EventInviteModel> getPendingEventInvitesByRecipientNickname(String nickname){
        return userWebClient.get()
                .uri("/event-invites/invites/{nickname}/pending", nickname)
                .retrieve()
                .bodyToFlux(EventInviteModel.class);
    }
    public Flux<EventInviteModel> getAcceptedEventInvitesByRecipientNickname(String nickname){
        return userWebClient.get()
                .uri("/event-invites/invites/{nickname}/accepted", nickname)
                .retrieve()
                .bodyToFlux(EventInviteModel.class);
    }
    public Flux<EventInviteModel> getRejectedEventInvitesByRecipientNickname(String nickname){
        return userWebClient.get()
                .uri("/event-invites/invites/{nickname}/rejected", nickname)
                .retrieve()
                .bodyToFlux(EventInviteModel.class);
    }
    public Flux<EventInviteModel> getInvitesToEventBySender(String nickname){
        return userWebClient.get()
                .uri("/event-invites/sender/nickname/{nickname}", nickname)
                .retrieve()
                .bodyToFlux(EventInviteModel.class);
    }
    public Mono<String> acceptInvToEvent(String recipientEmail, int senderId){
        return userWebClient.get()
                .uri("/event-invites/accept/{email}/{senderid}", recipientEmail, senderId)
                .retrieve()
                .bodyToMono(String.class);
    }
    public Mono<String> rejectInvToEvent(String recipientEmail, int senderId){
        return userWebClient.get()
                .uri("/event-invites/reject/{email}/{senderid}", recipientEmail, senderId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> deleteEvent(Long eventId){
        return userWebClient.get()
                .uri("/events/delete/{id}", eventId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
