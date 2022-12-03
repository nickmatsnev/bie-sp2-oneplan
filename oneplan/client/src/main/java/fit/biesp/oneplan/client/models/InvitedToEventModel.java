package fit.biesp.oneplan.client.models;

import java.util.List;

public class InvitedToEventModel {
    private List<UserModel> users;
    private List<FriendModel> friends;

    public InvitedToEventModel(List<UserModel> users, List<FriendModel> friends) {
        this.users = users;
        this.friends = friends;
    }

    public InvitedToEventModel() {
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public List<FriendModel> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendModel> friends) {
        this.friends = friends;
    }
}
