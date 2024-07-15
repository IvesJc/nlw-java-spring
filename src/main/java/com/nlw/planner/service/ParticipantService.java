package com.nlw.planner.service;

import com.nlw.planner.dto.participants.ParticipantCreateDTO;
import com.nlw.planner.dto.participants.ParticipantDataDTO;
import com.nlw.planner.dto.participants.ParticipantsRequestDTO;
import com.nlw.planner.model.Participant;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repository.ParticipantRepository;
import com.nlw.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private TripRepository tripRepository;

    public void registerParticipants(List<String> participantsToInvite, Trip trip){
        List<Participant> participantList = participantsToInvite.stream().map(email -> new Participant(email,
                trip)).toList();

        this.participantRepository.saveAll(participantList);
        System.out.println(participantList.getFirst().getId());
    }

    public ParticipantCreateDTO inviteParticipants(UUID id, ParticipantsRequestDTO participantsRequestDTO) {
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();

            ParticipantCreateDTO participantCreateDTO =
                    this.registerParticipantToEvent(participantsRequestDTO.email(),
                            newTrip);

            if (newTrip.isConfirmed())
                this.triggerConfirmationEmailToParticipant(participantsRequestDTO.email());
            return participantCreateDTO;
        }
        throw new RuntimeException();
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
