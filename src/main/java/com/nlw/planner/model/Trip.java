package com.nlw.planner.model;

import com.nlw.planner.dto.TripRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "trips")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String destination;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;


    public Trip(TripRequestDTO tripRequestDTO) {
        this.destination = tripRequestDTO.destination();
        this.startsAt = LocalDateTime.parse(tripRequestDTO.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.endsAt = LocalDateTime.parse(tripRequestDTO.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.isConfirmed = false;
        this.ownerEmail = tripRequestDTO.owner_email();
        this.ownerName = tripRequestDTO.owner_name();
    }
}