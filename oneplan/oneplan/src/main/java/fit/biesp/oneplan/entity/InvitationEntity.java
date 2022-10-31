package fit.biesp.oneplan.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitation", schema = "public", catalog = "postgres")
public class InvitationEntity {
    @Basic
    @Column(name = "user_id")
    private int userId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invitation_id")
    private int invitationId;
    @Basic
    @Column(name = "status")
    private int status;
    @Basic
    @Column(name = "receiver_email")
    private String receiverEmail;

    public InvitationEntity(int userId, int status, String receiverEmail) {
        this.userId = userId;
        this.status = status;
        this.receiverEmail = receiverEmail;
    }
    public InvitationEntity(int userId, String receiverEmail) {
        this.userId = userId;
        this.status = 0;
        this.receiverEmail = receiverEmail;
    }

    public InvitationEntity() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvitationEntity that = (InvitationEntity) o;
        return userId == that.userId && invitationId == that.invitationId && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, invitationId, status);
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
