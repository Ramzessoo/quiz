package com.example.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HealthcheckDto {
    private boolean status;
    private String message;

}
