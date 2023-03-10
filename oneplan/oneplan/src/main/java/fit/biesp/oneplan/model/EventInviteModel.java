package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.EventInvitationsEntity;
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

    public static EventInvitationsEntity fromModel(EventInviteModel model){
        EventInvitationsEntity entity = new EventInvitationsEntity();
        entity.setEventId(EventModel.fromModel(model.getEventModel()));
        entity.setStatus(model.getStatus());
        entity.setSenderId(UserModel.fromModel(model.getSender()));
        entity.setRecipientEmail(model.getRecipientEmail());
        return entity;
    }
    public static EventInviteModel toModel(EventInvitationsEntity entity){
        EventInviteModel model = new EventInviteModel();
        model.setEventModel(EventModel.toModel(entity.getEventId()));
        model.setSender(UserModel.toModel(entity.getSenderId()));
        model.setRecipientEmail(entity.getRecipientEmail());
        model.setStatus(entity.getStatus());
        return model;
    }

}
