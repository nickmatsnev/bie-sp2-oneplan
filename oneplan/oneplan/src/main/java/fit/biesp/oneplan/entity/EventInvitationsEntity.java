package fit.biesp.oneplan.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "event_invitations", schema = "public", catalog = "postgres")
public class EventInvitationsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "eiid")
    private long eiid;
    @Basic
    @Column(name = "status")
    private int status;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
    @Basic
    @Column(name = "recipient_email")
    private String recipientEmail;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    public long getEiid() {
        return eiid;
    }

    public void setEiid(long eiid) {
        this.eiid = eiid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserEntity getSenderId() {
        return sender;
    }

    public void setSenderId(UserEntity senderId) {
        this.sender = senderId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public EventEntity getEventId() {
        return event;
    }

    public void setEventId(EventEntity eventId) {
        this.event = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventInvitationsEntity that = (EventInvitationsEntity) o;
        return eiid == that.eiid && status == that.status && sender == that.sender && event == that.event && Objects.equals(recipientEmail, that.recipientEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eiid, status, sender, recipientEmail, event);
    }
}
