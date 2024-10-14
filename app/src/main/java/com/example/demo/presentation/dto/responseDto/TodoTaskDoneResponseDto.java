package com.example.demo.presentation.dto.responseDto;

import java.time.LocalDateTime;

public record TodoTaskDoneResponseDto(
        String id, String title, String content, LocalDateTime createdAt, boolean taskDone) {
}
