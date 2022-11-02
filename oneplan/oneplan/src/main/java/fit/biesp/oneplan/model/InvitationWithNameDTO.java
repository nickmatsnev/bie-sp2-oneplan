package fit.biesp.oneplan.model;

public class InvitationWithNameDTO {
    private String userName;
    private String receiverEmail;
    private int invitationId;

    public InvitationWithNameDTO() {
    }

    public InvitationWithNameDTO(String userName, String receiverEmail, int invitationId) {
        this.userName = userName;
        this.receiverEmail = receiverEmail;
        this.invitationId = invitationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
