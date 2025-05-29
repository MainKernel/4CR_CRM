package com.recruiting.center.crm.dto.entity;

import jakarta.validation.constraints.NotBlank;

public record DocumentDto(
        @NotBlank(message = "Назва файлу обов'язкова")
        String fileName,

        @NotBlank(message = "Оригінальна назва файлу обов'язкова")
        String originalFileName,

        @NotBlank(message = "Тип контенту обов'язковий")
        String contentType,

        @NotBlank(message = "Вміст файлу обов'язковий")
        String fileContent
) {}