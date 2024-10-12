package com.example.demo.presentation.dto;

import java.time.LocalDateTime;

public record TodoResponseDto(
        String id, String title, String content, LocalDateTime createdAt) {
}
