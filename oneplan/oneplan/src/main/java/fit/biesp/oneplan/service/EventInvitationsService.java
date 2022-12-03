package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.EventInvitationsEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.EventInviteModel;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.repository.EventInvitationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventInvitationsService {
    @Autowired
    private EventInvitationsRepository eventInvitationsRepository;


    public String create(EventInvitationsEntity eventInvitationsEntity){
        var message = "Invitation to event successfully created";
        eventInvitationsRepository.save(eventInvitationsEntity);
        return message;
    }
    public List<EventInvitationsEntity> getEntitiesByEmail(String email){
        return eventInvitationsRepository.getAllByRecipientEmail(email);
    }
    public List<EventInvitationsEntity> getEntitiesBySender(UserEntity sender){
        return eventInvitationsRepository.getAllBySender(sender);
    }

    public String acceptByRecipientEmailAndSenderId(String email, UserEntity user){
        EventInvitationsEntity entity = eventInvitationsRepository.getByRecipientEmailAndSender(email, user);
        eventInvitationsRepository.save(entity);
        return "Accepted!";
    }
    public String rejectByRecipientEmailAndSenderId(String email, UserEntity user){
        EventInvitationsEntity entity = eventInvitationsRepository.getByRecipientEmailAndSender(email, user);
        entity.setStatus(2);
        eventInvitationsRepository.save(entity);
        return "Rejected!";
    }

    public EventInvitationsEntity getByEmailAndSenderId(String email, UserEntity user){
        EventInvitationsEntity entity = eventInvitationsRepository.getByRecipientEmailAndSender(email, user);
        return entity;
    }

    public List<EventInvitationsEntity> getAllByEvent(EventEntity event){
        return   eventInvitationsRepository.getAllByEvent(event);
    }

    public String delete(EventInvitationsEntity entity){
        eventInvitationsRepository.delete(entity);
        return "successfully deleted " + entity.getEventId();
    }
    public String deleteByRecipientEmailAndSenderId(String recipientEmail, UserEntity sender){
        EventInvitationsEntity entity = eventInvitationsRepository.getByRecipientEmailAndSender(recipientEmail, sender);
        eventInvitationsRepository.delete(entity);
        return "successfully deleted " + entity.getEventId();
    }
}
