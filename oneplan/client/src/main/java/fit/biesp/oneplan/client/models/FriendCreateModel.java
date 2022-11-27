package fit.biesp.oneplan.client.models;

public class FriendCreateModel {
    private String email;
    private int userId;

    public FriendCreateModel() {
    }

    public FriendCreateModel(String email, String nickname, int userId) {
        this.email = email;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
