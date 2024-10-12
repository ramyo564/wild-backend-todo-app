package com.example.demo.presentation.dto;

import java.time.LocalDateTime;

public record TodoDeletedResponseDto(
        String id, String title, String content, LocalDateTime deletedAt, boolean isDeleted) {
}
