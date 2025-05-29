package com.recruiting.center.crm.controller;

import com.recruiting.center.crm.dto.request.LoginRequestDto;
import com.recruiting.center.crm.dto.response.LoginResponseDto;
import com.recruiting.center.crm.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;


    @PostMapping
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), loginRequest.password()));
            log.debug("LoginController: Successful authentication");
        } catch (BadCredentialsException ex){
            log.error(" LoginController: Bad credentials", ex);
            return new LoginResponseDto("", HttpStatus.BAD_REQUEST);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
        String token = jwtUtils.generateToken(userDetails);
        return new LoginResponseDto(token, HttpStatus.OK);
    }
}
