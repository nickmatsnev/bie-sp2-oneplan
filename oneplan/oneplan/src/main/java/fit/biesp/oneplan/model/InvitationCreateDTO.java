package fit.biesp.oneplan.model;

public class InvitationCreateDTO {
    Integer sender_id;
    String recipient_email;

    public InvitationCreateDTO(Integer sender_id, String recipient_email) {
        this.sender_id = sender_id;
        this.recipient_email = recipient_email;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }
}
