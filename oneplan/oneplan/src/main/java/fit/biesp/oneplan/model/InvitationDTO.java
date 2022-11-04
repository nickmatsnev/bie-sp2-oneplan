package fit.biesp.oneplan.model;

public class InvitationDTO {
    private int userId;
    private String receiverEmail;
    private int invitationId;

    public InvitationDTO() {
    }

    public InvitationDTO(int userId, String receiverEmail, int invitationId) {
        this.userId = userId;
        this.receiverEmail = receiverEmail;
        this.invitationId = invitationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }
}
