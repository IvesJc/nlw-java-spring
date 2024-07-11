package com.nlw.planner.controller;

import com.nlw.planner.dto.TripRequestDTO;
import com.nlw.planner.dto.TripResponseDTO;
import com.nlw.planner.model.Trip;
import com.nlw.planner.repositories.TripRepository;
import com.nlw.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<Trip> findById(@PathVariable UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(@RequestBody TripRequestDTO tripRequestDTO) {
        Trip trip = new Trip(tripRequestDTO);

        this.tripRepository.save(trip);
        this.participantService.registerParticipants(tripRequestDTO.emails_to_invite(), trip.getId());
        return ResponseEntity.ok(new TripResponseDTO(trip.getId()));
    }

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
}
