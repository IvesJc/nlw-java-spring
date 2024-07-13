package com.nlw.planner.dto.participants;

import java.util.UUID;

public record ParticipantDataDTO(
        UUID id,
        String name,
        String email,
        Boolean isConfirmed
) {
}
