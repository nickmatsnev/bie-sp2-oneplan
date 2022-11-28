package fit.biesp.oneplan.client.models;

public class EventInviteRealModel {
    private String recipientEmail;
    private int senderId;
    private int eventModelId;

    public EventInviteRealModel() {
    }

    public EventInviteRealModel(String recipientEmail, int senderId, int eventModelId) {
        this.recipientEmail = recipientEmail;
        this.senderId = senderId;
        this.eventModelId = eventModelId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getEventModelId() {
        return eventModelId;
    }

    public void setEventModelId(int eventModelId) {
        this.eventModelId = eventModelId;
    }

}
