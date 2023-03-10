package fit.biesp.oneplan.client.models;

public class FriendCreateModel {
    private String email, name;
    private int userId;

    public FriendCreateModel() {
    }

    public FriendCreateModel(String email, String name, int userId) {
        this.email = email;
        this.userId = userId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
