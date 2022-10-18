package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.InvitationEntity;
import fit.biesp.oneplan.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
public class InvitationService {
    @Autowired
    private final InvitationRepository invitationRepository;

    public InvitationService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public void delete(Integer invitationId){
        invitationRepository.deleteById(invitationId);
    }

    public InvitationEntity create(InvitationEntity invitationEntity){
        InvitationEntity optEnt = invitationRepository.findInvitationEntityByInvitationId(invitationEntity.getInvitationId());
        if(optEnt != null){
            throw new EntityExistsException();
        }
        return invitationRepository.save(invitationEntity);
    }
    public List<InvitationEntity> findAllByUserId(Integer userId){ return invitationRepository.findInvitationEntitiesByUserId(userId);}
    public InvitationEntity findByInvitationId(Integer invitationId){ return invitationRepository.findInvitationEntityByInvitationId(invitationId);}

}
