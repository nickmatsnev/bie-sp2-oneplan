package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.exception.UserAlreadyExistsException;
import fit.biesp.oneplan.exception.UserNotFoundException;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.model.UserRegistrationModel;
import fit.biesp.oneplan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserModel registration(UserRegistrationModel userModel) throws UserAlreadyExistsException {
        if(userRepository.findByNickname(userModel.getNickname()) != null)
            throw new UserAlreadyExistsException("User with this nickname already exists!");

        if(userRepository.findByEmail(userModel.getEmail()) != null)
            throw new UserAlreadyExistsException("User with this email already exists!");

        return UserModel.toModel(userRepository.save(UserRegistrationModel.fromModel(userModel)));
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

    public Long delete(String nickname) throws UserNotFoundException {
        if(userRepository.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        UserEntity user = userRepository.findByNickname(nickname);
        Long id = user.getId();

        userRepository.deleteById(id);
        return id;
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
        return password.equals(user.getPassword());
    }

    public UserEntity findByNickname(String nickname) {
        return findOrThrow(nickname);
    }
    private UserEntity findOrThrow(String nickname) {
        UserEntity optionalUser = userRepository.findByNickname(nickname);
        if (optionalUser == null) {
            throw new IllegalArgumentException();
        }
        return optionalUser;
    }

}
