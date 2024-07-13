package com.nlw.planner.dto.activity;

import java.time.LocalDateTime;

public record ActivityRequestDTO(
        String title,
        String occursAt
) {
}
