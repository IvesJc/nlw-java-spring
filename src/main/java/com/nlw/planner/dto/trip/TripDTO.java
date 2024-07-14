package com.nlw.planner.dto.trip;

import java.util.List;
import java.util.UUID;

public record TripDTO(
        UUID id,
        String destination,
        String starts_at,
        String ends_at,
        List<String> emails_to_invite,
        String owner_email,
        String owner_name
) {
}
