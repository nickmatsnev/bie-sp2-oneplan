package fit.biesp.oneplan.service;

import fit.biesp.oneplan.model.EventInviteModel;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.repository.EventInvitationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventInvitationsService {
    @Autowired
    private EventInvitationsRepository eventInvitationsRepository;

    public String create(EventInviteModel eventInviteModel){
        var message = "Invitation to event successfully created";
        eventInvitationsRepository.save(EventInviteModel.fromModel(eventInviteModel));
        return message;
    }
}
