package com.nlw.planner.controller;

import com.nlw.planner.dto.participants.ParticipantsRequestDTO;
import com.nlw.planner.model.Participant;
import com.nlw.planner.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

   @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipants(@PathVariable UUID id,
                                                           @RequestBody ParticipantsRequestDTO participantsDTO){
       Optional<Participant> participant = participantRepository.findById(id);

       if (participant.isPresent()) {
           Participant newParticipant = participant.get();
           newParticipant.setConfirmed(true);
           newParticipant.setName(participantsDTO.name());


           this.participantRepository.save(newParticipant);
           return ResponseEntity.ok(newParticipant);
       }

       return ResponseEntity.notFound().build();
   }

}
