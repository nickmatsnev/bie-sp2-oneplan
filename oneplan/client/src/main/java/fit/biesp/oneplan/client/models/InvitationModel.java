package fit.biesp.oneplan.client.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationModel {
    private int userId; //  sender id
    private int receiverId;
    private int invitationId; // inv id
    private int status; // 0 - pending, 1- accepted, 2- rejected
    private String receiverEmail;
}
