package com.nlw.planner.service;

import com.nlw.planner.dto.participants.ParticipantCreateDTO;
import com.nlw.planner.dto.participants.ParticipantDataDTO;
import com.nlw.planner.model.Participant;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipants(List<String> participantsToInvite, Trip trip){
        List<Participant> participantList = participantsToInvite.stream().map(email -> new Participant(email,
                trip)).toList();

        this.participantRepository.saveAll(participantList);
        System.out.println(participantList.getFirst().getId());
    }

    public ParticipantCreateDTO registerParticipantToEvent(String email, Trip trip){
        Participant newParticipant = new Participant(email, trip);
        this.participantRepository.save(newParticipant);

        return new ParticipantCreateDTO(newParticipant.getId());
    }
    public void triggerConfirmationEmailToParticipants(UUID tripId){

    }
    public void triggerConfirmationEmailToParticipant(String email){

    }

    public List<ParticipantDataDTO> getAllParticipantsFromEvent(UUID tripId){
        return this.participantRepository.findByTripId(tripId).
                stream().
                map(participant -> new ParticipantDataDTO(
                        participant.getId(),
                        participant.getName(),
                        participant.getEmail(),
                        participant.isConfirmed())).
                toList();
    }
}
