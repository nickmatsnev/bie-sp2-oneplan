package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.FriendEntity;

public class FriendCreateModel {
    private String email;
    private int userId;

    public FriendCreateModel() {
    }

    public FriendCreateModel(String email, int userId) {
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

    public FriendEntity fromFullModel(){
        return new FriendEntity(getEmail(),
                getUserId());
    }
}
