package com.nlw.planner.controller;

import com.nlw.planner.dto.TripRequestDTO;
import com.nlw.planner.dto.TripResponseDTO;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.TripRepository;
import com.nlw.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Trip> findById(@PathVariable UUID id){
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(@RequestBody TripRequestDTO tripRequestDTO){
        Trip trip = new Trip(tripRequestDTO);

        this.tripRepository.save(trip);
        this.participantService.registerParticipants(tripRequestDTO.emails_to_invite(), trip.getId());
        return ResponseEntity.ok(new TripResponseDTO(trip.getId()));
    }


}
