package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.request.RegistrationRequestDto;
import com.recruiting.center.crm.dto.response.RegistrationResponseDto;
import com.recruiting.center.crm.service.controller.RegistrationControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationControllerService registrationControllerService;

    @PostMapping
    public RegistrationResponseDto register(@RequestBody RegistrationRequestDto requestDto) {
        return registrationControllerService.register(requestDto);
    }
}
