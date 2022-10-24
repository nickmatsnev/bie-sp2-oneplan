package fit.biesp.oneplan.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitation", schema = "public", catalog = "postgres")
public class InvitationEntity {
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "receiver_id")
    private int receiverId;
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

    public InvitationEntity(int userId, int receiverId, int invitationId, int status, String receiverEmail) {
        this.userId = userId;
        this.receiverId = receiverId;
        this.invitationId = invitationId;
        this.status = status;
        this.receiverEmail = receiverEmail;
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

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
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
        return userId == that.userId && receiverId == that.receiverId && invitationId == that.invitationId && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, receiverId, invitationId, status);
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
