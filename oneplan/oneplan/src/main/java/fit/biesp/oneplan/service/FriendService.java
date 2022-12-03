package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.model.FriendCreateModel;
import fit.biesp.oneplan.model.FriendModel;
import fit.biesp.oneplan.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    public FriendEntity create(FriendEntity friend){
        return friendRepository.save(friend);
    }

    public FriendModel addFriend(FriendModel friendModel){
        return FriendModel.toModel(friendRepository.save(FriendModel.fromModel(friendModel)));
    }
    public void addFriendNew(FriendCreateModel friendModel){
        friendRepository.save(friendModel.fromFullModel());
    }

    public FriendModel getFriend(String nickname){
        FriendEntity friend = friendRepository.findByNickname(nickname);
        return FriendModel.toModel(friend);
    }
    public FriendModel getFriendById(long id){
        FriendEntity friend = friendRepository.findById(id);
        return FriendModel.toModel(friend);
    }
    public FriendModel findFriendByEmailAndUserId(String email, long userId){
        FriendEntity friend = friendRepository.findByUserIdAndEmail((int) userId, email);
        return FriendModel.toModel(friend);
    }
    public FriendModel updateFriend(FriendModel friendModel, String nickname){
        FriendEntity friend = friendRepository.findByNickname(nickname);
        friend.setNickname(friendModel.getNickname());
        friend.setEmail(friendModel.getEmail());

        return FriendModel.toModel(friendRepository.save(friend));
    }
    public FriendModel updateFriendById(FriendModel friendModel, long id){
        FriendEntity friend = friendRepository.findById(id);
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

    public Long deleteByUserIdAndEmail(String email, int userId){
        FriendEntity friend = friendRepository.findByUserIdAndEmail(userId, email);
        friendRepository.deleteById(friend.getId());
        return friend.getId();
    }

    public List<FriendEntity> findAllByUserId(Integer userId){ return friendRepository.findAllByUserId(userId);}
}
