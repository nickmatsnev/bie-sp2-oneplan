package fit.biesp.oneplan.client.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventInviteModel {
    private String recipientEmail;
    private UserModel sender;
    private EventModel eventModel;
    private int status;

}
