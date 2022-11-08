package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.exception.PersonNotFoundException;
import fit.biesp.oneplan.model.FriendModel;
import fit.biesp.oneplan.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    public FriendEntity create(FriendEntity friend) {
        return friendRepository.save(friend);
    }

    public FriendModel addFriend(FriendModel friendModel) {
        return FriendModel.toModel(friendRepository.save(FriendModel.fromModel(friendModel)));
    }

    public FriendModel getFriend(String nickname) throws PersonNotFoundException {
        FriendEntity friend = friendRepository.findByNickname(nickname);
        if (friend == null) throw new PersonNotFoundException("");
        return FriendModel.toModel(friend);
    }

    public FriendModel updateFriend(FriendModel friendModel, String nickname) throws PersonNotFoundException {
        FriendEntity friend = friendRepository.findByNickname(nickname);
        if (friend == null) throw new PersonNotFoundException("");
        friend.setNickname(friendModel.getNickname());
        friend.setEmail(friendModel.getEmail());

        return FriendModel.toModel(friendRepository.save(friend));
    }

    public Long delete(String nickname) throws PersonNotFoundException {
        FriendEntity friend = friendRepository.findByNickname(nickname);
        if (friend == null) throw new PersonNotFoundException("");
        Long id = friend.getId();
        friendRepository.deleteById(id);
        return id;
    }

    public List<FriendEntity> findAllByUserId(Integer userId) {
        return friendRepository.findAllByUserId(userId);
    }
}
