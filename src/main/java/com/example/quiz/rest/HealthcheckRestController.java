package com.example.quiz.rest;

import com.example.quiz.dto.HealthcheckDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthcheckRestController {

    @GetMapping
    public HealthcheckDto healthcheck() {
        return new HealthcheckDto(true, "It's working!");
    }
}
