package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.response.ApplicationStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    @GetMapping
    public ApplicationStatusDto status() {
        return new ApplicationStatusDto("Healthy", HttpStatus.OK);
    }


}
