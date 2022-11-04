package fit.biesp.oneplan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class FriendFullModel {
    private int friendId, userId;
    private String email, nickname;

    public FriendFullModel() {
    }

    public FriendFullModel(int friendId, int userId, String email, String nickname) {
        this.friendId = friendId;
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
