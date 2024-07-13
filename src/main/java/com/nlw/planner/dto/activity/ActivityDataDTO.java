package com.nlw.planner.dto.activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDataDTO(
        UUID id,
        String title,
        LocalDateTime occursAt
) {
}
