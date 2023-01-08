package fit.biesp.oneplan.service;

import com.sendgrid.helpers.mail.Mail;
import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.PersonEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.exception.UserAlreadyExistsException;
import fit.biesp.oneplan.exception.UserNotFoundException;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.model.UserRegistrationModel;
import fit.biesp.oneplan.repository.PersonRepository;
import fit.biesp.oneplan.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonRepository personRepository;
    private final String clientUrl;

    public UserService(@Value("${client.url}") String clientUrl) {
        this.clientUrl = clientUrl;
    }

    private String getRandomStringOfSize(int size){
        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public UserModel registration(UserRegistrationModel userModel) throws UserAlreadyExistsException, IOException {
        if(userRepository.findByNickname(userModel.getNickname()) != null)
            throw new UserAlreadyExistsException("User with this nickname already exists!");

        if(userRepository.findByEmail(userModel.getEmail()) != null)
            throw new UserAlreadyExistsException("User with this email already exists!");
        String verifyLink = clientUrl + "/verify/" + userModel.getEmail();
        UserEntity user = UserRegistrationModel.fromModel(userModel);
        user.setSecret(getRandomStringOfSize(64));
        userRepository.save(user);
        MailService.verifyEmail(userModel.getEmail(), verifyLink, user.getSecret());

        return UserModel.toModel(user);
    }

    public UserModel getUser(String nickname) throws UserNotFoundException {
        if(userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        var user = userRepository.findByNickname(nickname);

        return UserModel.toModel(user);
    }

    public String getPassword (String nickname) throws UserNotFoundException {
        if(userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        var user = userRepository.findByNickname(nickname);
        return user.getPassword();
    }

    public UserModel updateUser(UserModel userModel, String nickname) throws UserNotFoundException {
        if (userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        UserEntity user = userRepository.findByNickname(nickname);
        user.setNickname(userModel.getNickname());
        user.setEmail(userModel.getEmail());

        return UserModel.toModel(userRepository.save(user));
    }
    public UserEntity updateUserEntity(UserEntity user, String nickname) throws UserNotFoundException {
        if (userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        return userRepository.save(user);
    }

    public Long delete(String nickname) throws UserNotFoundException {
        if(userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        UserEntity user = userRepository.findByNickname(nickname);
        Long id = user.getId();

        userRepository.deleteById(id);
        return id;
    }
    public List<EventEntity> getEvents(String nickname)throws UserNotFoundException {
        if(userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        UserEntity user = userRepository.findByNickname(nickname);
        Optional<PersonEntity> optionalPersonEntity = personRepository.findById(user.getId());
        PersonEntity person = optionalPersonEntity.get();
        var all = person.getEventsToAttend();
        all.addAll(user.getEvents());
        for(var i : person.getEventsToAttend()){
            System.out.println("events where person is attendee : " + i.getName());
        }
        return all;
    }
    public Collection<EventModel> getOrganized(String nickname) throws UserNotFoundException {
        if(userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        var user = userRepository.findByNickname(nickname);
        Set<EventModel> events = new HashSet<>();
        for(Iterator<EventEntity> it = user.getEvents().iterator(); it.hasNext(); ){
            EventEntity x = it.next();
            events.add(EventModel.toModel(x));
        }
        return events;
    }

    public boolean loginUser(String nickname, String password) {
        UserEntity user = findOrThrow(nickname);
        return password.equals(user.getPassword()) && user.getStatus() == 1;
    }

    public UserEntity findByNickname(String nickname) {
        return findOrThrow(nickname);
    }
    public UserEntity findbyId(Long id) {
        return findOrThrow(id);
    }
    private UserEntity findOrThrow(String nickname) {
        UserEntity optionalUser = userRepository.findByNickname(nickname);
        if (optionalUser == null) {
            throw new IllegalArgumentException();
        }
        return optionalUser;
    }
    public UserEntity findBySecret(String secret){
        return userRepository.findUserEntityBySecret(secret);
    }
    public UserEntity findByEmail(String email){
        UserEntity optionalUser = userRepository.findByEmail(email);
        if (optionalUser == null) {
            UserEntity newUser = new UserEntity();
            newUser.setId((long) -2);
            return newUser;
        }
        return optionalUser;
    }

    public List<UserEntity> getAll(){ return userRepository.findAll(); }

    private UserEntity findOrThrow(Long id) {
        UserEntity optionalUser = userRepository.findUserEntityById(id);
        if (optionalUser == null) {
            throw new IllegalArgumentException();
        }
        return optionalUser;
    }

    public UserEntity getById(long id){
        return userRepository.getById(id);
    }

}
