package fit.biesp.oneplan.model;

public class InvitationDTO {
    private int userId;
    private int receiverId;
    private int invitationId;

    public InvitationDTO(int userId, int receiverId, int invitationId) {
        this.userId = userId;
        this.receiverId = receiverId;
        this.invitationId = invitationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }
}
