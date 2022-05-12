package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.model.FriendModel;
import fit.biesp.oneplan.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    public FriendModel addFriend(FriendModel friendModel){
        return FriendModel.toModel(friendRepository.save(FriendModel.fromModel(friendModel)));
    }

    public FriendModel getFriend(String nickname){
        FriendEntity friend = friendRepository.findByNickname(nickname);
        return FriendModel.toModel(friend);
    }

    public FriendModel updateFriend(FriendModel friendModel, String nickname){
        FriendEntity friend = friendRepository.findByNickname(nickname);
        friend.setNickname(friendModel.getNickname());
        friend.setEmail(friendModel.getEmail());

        return FriendModel.toModel(friendRepository.save(friend));
    }

    public Long delete(String nickname){
        FriendEntity friend = friendRepository.findByNickname(nickname);
        Long id = friend.getId();
        friendRepository.deleteById(id);
        return id;
    }
}
