package com.nlw.planner.controller;

import com.nlw.planner.dto.activity.ActivityDataDTO;
import com.nlw.planner.dto.activity.ActivityRequestDTO;
import com.nlw.planner.dto.activity.ActivityResponseDTO;
import com.nlw.planner.dto.link.LinkDataDTO;
import com.nlw.planner.dto.link.LinkRequestDTO;
import com.nlw.planner.dto.link.LinkResponseDTO;
import com.nlw.planner.dto.participants.ParticipantCreateDTO;
import com.nlw.planner.dto.participants.ParticipantDataDTO;
import com.nlw.planner.dto.participants.ParticipantsRequestDTO;
import com.nlw.planner.dto.trip.TripRequestDTO;
import com.nlw.planner.dto.trip.TripResponseDTO;
import com.nlw.planner.model.Activity;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.ActivityRepository;
import com.nlw.planner.repositories.TripRepository;
import com.nlw.planner.service.ActivityService;
import com.nlw.planner.service.LinkService;
import com.nlw.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    // BUSCAR VIAGEM
    @GetMapping("/{id}")
    public ResponseEntity<Trip> findById(@PathVariable UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PARTICIPANTE CONFIRMAR VIAGEM
    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()){
            Trip newTrip = trip.get();
            newTrip.setConfirmed(true);

            this.tripRepository.save(newTrip);
            this.participantService.triggerConfirmationEmailToParticipants(id);
            return ResponseEntity.ok(newTrip);
        }

        return ResponseEntity.notFound().build();
    }

    // CRIAR VIAGEM
    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(@RequestBody TripRequestDTO tripRequestDTO) {
        Trip newTrip = new Trip(tripRequestDTO);

        this.tripRepository.save(newTrip);
        this.participantService.registerParticipants(
                tripRequestDTO.emails_to_invite(),
                newTrip);

        return ResponseEntity.ok(new TripResponseDTO(newTrip.getId()));
    }

    // ATUALIZAR VIAGEM
    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id,
                                           @RequestBody TripRequestDTO tripDTO) {
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();
            newTrip.setEndsAt(LocalDateTime.parse(tripDTO.ends_at(),
                    DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setEndsAt(LocalDateTime.parse(tripDTO.starts_at(),
                    DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setDestination(tripDTO.destination());
            this.tripRepository.save(newTrip);
            return ResponseEntity.ok(newTrip);
        }

        return ResponseEntity.notFound().build();
    }


    // BUSCAR TODOS PARTICIPANTES
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDataDTO>> getAllParticipants(@PathVariable UUID id){
        List<ParticipantDataDTO> participantList = this.participantService.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantList);
    }


    // CONVIDAR PARTICIPANTE
    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateDTO> inviteParticipant(@PathVariable UUID id,
                                                                  @RequestBody ParticipantsRequestDTO participantsDTO){
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()){
            Trip newTrip = trip.get();

            ParticipantCreateDTO participantCreateDTO =
                    this.participantService.registerParticipantToEvent(participantsDTO.email(),
                            newTrip);

            if (newTrip.isConfirmed()) this.participantService.triggerConfirmationEmailToParticipant(participantsDTO.email());

            return ResponseEntity.ok(participantCreateDTO);
        }
        return ResponseEntity.notFound().build();
    }

    // BUSCAR TODAS ATIVIDADES
    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDataDTO>> getAllActivities(@PathVariable UUID id){
        List<ActivityDataDTO> activityDataDTOS = this.activityService.getAllActivitiesFromEvent(id);

        return ResponseEntity.ok(activityDataDTOS);
    }

    // CADASTRO DE ATIVIDADES
    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponseDTO> registerActivity(@PathVariable UUID id,
                                                                @RequestBody ActivityRequestDTO activityRequestDTO){
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()){
            Trip newTrip = trip.get();

            ActivityResponseDTO activityResponseDTO = activityService.registerActivity(activityRequestDTO,
                    newTrip);

            return ResponseEntity.ok(activityResponseDTO);
        }

        return ResponseEntity.notFound().build();
    }


    // BUSCAR LINKS
    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDataDTO>> getAllLinks(@PathVariable UUID id){
        List<LinkDataDTO> linkDataDTOS = this.linkService.getAllLinksFromEvent(id);

        return ResponseEntity.ok(linkDataDTOS);
    }

    // CRIAR LINK
    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponseDTO> createLink(@PathVariable UUID id,
                                                      @RequestBody LinkRequestDTO linkRequestDTO){
        Optional<Trip> trip = tripRepository.findById(id);

        if (trip.isPresent()){
            Trip newTrip = trip.get();

            LinkResponseDTO linkResponseDTO = linkService.registerLink(linkRequestDTO, newTrip);
            return ResponseEntity.ok(linkResponseDTO);
        }
        return ResponseEntity.notFound().build();
    }


}
