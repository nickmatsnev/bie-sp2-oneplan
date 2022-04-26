package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.exceptions.UserAlreadyExistsException;
import fit.biesp.oneplan.exceptions.UserNotFoundException;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.model.UserRegistrationModel;
import fit.biesp.oneplan.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserModel registration(UserRegistrationModel userModel) throws UserAlreadyExistsException {
        if(userRepo.findByNickname(userModel.getNickname()) != null)
            throw new UserAlreadyExistsException("User with this nickname already exists!");

        if(userRepo.findByEmail(userModel.getEmail()) != null)
            throw new UserAlreadyExistsException("User with this email already exists!");

        return UserModel.toModel(userRepo.save(UserRegistrationModel.fromModel(userModel)));
    }

    public UserModel getUser(String nickname) throws UserNotFoundException {
        if(userRepo.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        var customer = userRepo.findByNickname(nickname);

        return UserModel.toModel(customer);
    }

    public UserModel updateUser(UserModel userModel, String nickname) throws UserNotFoundException {
        if (userRepo.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        UserEntity user = userRepo.findByNickname(nickname);
        user.setNickname(userModel.getNickname());
        user.setEmail(userModel.getEmail());

        return UserModel.toModel(userRepo.save(user));
    }

    public Long delete(String nickname) throws UserNotFoundException {
        if(userRepo.findByNickname(nickname) == null)
            throw new UserNotFoundException("User not found!");

        UserEntity customer = userRepo.findByNickname(nickname);
        Long id = customer.getId();

        userRepo.deleteById(id);
        return id;
    }
}
