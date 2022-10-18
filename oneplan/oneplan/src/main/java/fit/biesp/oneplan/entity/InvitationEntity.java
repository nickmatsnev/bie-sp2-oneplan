package fit.biesp.oneplan.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitations", schema = "public", catalog = "postgres")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvitationEntity that = (InvitationEntity) o;
        return userId == that.userId && receiverId == that.receiverId && invitationId == that.invitationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, receiverId, invitationId);
    }
}
