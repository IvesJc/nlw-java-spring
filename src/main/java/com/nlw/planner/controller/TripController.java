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
import com.nlw.planner.dto.trip.TripDTO;
import com.nlw.planner.dto.trip.TripRequestDTO;
import com.nlw.planner.dto.trip.TripResponseDTO;
import com.nlw.planner.model.Activity;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.ActivityRepository;
import com.nlw.planner.repositories.TripRepository;
import com.nlw.planner.service.ActivityService;
import com.nlw.planner.service.LinkService;
import com.nlw.planner.service.ParticipantService;
import com.nlw.planner.service.TripService;
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
    private TripService tripService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    // BUSCAR VIAGEM
    @GetMapping("/{id}")
    public ResponseEntity<Trip> findById(@PathVariable UUID id) {
        Trip trip = tripService.findById(id);
        return ResponseEntity.ok(trip);
    }

    // PARTICIPANTE CONFIRMAR VIAGEM
    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Trip trip = tripService.confirmTrip(id);
        return ResponseEntity.ok(trip);
    }

    // CRIAR VIAGEM
    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(@RequestBody TripRequestDTO tripRequestDTO) {
        Trip newTrip = tripService.createTrip(tripRequestDTO);
        return ResponseEntity.ok(new TripResponseDTO(newTrip.getId()));
    }

    // ATUALIZAR VIAGEM
    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id,
                                           @RequestBody TripRequestDTO tripDTO) {
        Trip renewTrip = tripService.updateTrip(id, tripDTO);

        return ResponseEntity.ok(renewTrip);
    }


    // BUSCAR TODOS PARTICIPANTES
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDataDTO>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantDataDTO> participantList = this.participantService.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantList);
    }


    // CONVIDAR PARTICIPANTE
    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateDTO> inviteParticipant(@PathVariable UUID id,
                                                                  @RequestBody ParticipantsRequestDTO participantsDTO) {

        ParticipantCreateDTO participantCreateDTO = participantService.inviteParticipants(id,
                participantsDTO);
        return ResponseEntity.ok(participantCreateDTO);
    }


    // BUSCAR TODAS ATIVIDADES
    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDataDTO>> getAllActivities(@PathVariable UUID id) {
        List<ActivityDataDTO> activityDataDTOS = this.activityService.getAllActivitiesFromEvent(id);

        return ResponseEntity.ok(activityDataDTOS);
    }

    // CADASTRO DE ATIVIDADES
    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponseDTO> registerActivity(@PathVariable UUID id,
                                                                @RequestBody ActivityRequestDTO activityRequestDTO) {

        ActivityResponseDTO activityResponseDTO = activityService.registerActivity(activityRequestDTO, id);
        return ResponseEntity.ok(activityResponseDTO);
    }


    // BUSCAR LINKS
    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDataDTO>> getAllLinks(@PathVariable UUID id) {
        List<LinkDataDTO> linkDataDTOS = this.linkService.getAllLinksFromEvent(id);

        return ResponseEntity.ok(linkDataDTOS);
    }

    // CRIAR LINK
    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponseDTO> createLink(@PathVariable UUID id,
                                                      @RequestBody LinkRequestDTO linkRequestDTO) {
        LinkResponseDTO linkResponseDTO = linkService.registerLink(linkRequestDTO, id);
        return ResponseEntity.ok(linkResponseDTO);
    }


}
