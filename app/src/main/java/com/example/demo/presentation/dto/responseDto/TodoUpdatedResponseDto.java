package com.example.demo.presentation.dto.responseDto;

import java.time.LocalDateTime;

public record TodoUpdatedResponseDto(
        String id, String title, String content, LocalDateTime updatedAt) {
}
