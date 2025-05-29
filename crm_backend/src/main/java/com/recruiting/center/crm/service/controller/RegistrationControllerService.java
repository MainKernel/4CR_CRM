package com.recruiting.center.crm.service.controller;

import com.recruiting.center.crm.dto.request.RegistrationRequestDto;
import com.recruiting.center.crm.dto.response.RegistrationResponseDto;
import com.recruiting.center.crm.entity.appuser.AppUser;
import com.recruiting.center.crm.entity.appuser.UserPosition;
import com.recruiting.center.crm.entity.appuser.WorkUnit;
import com.recruiting.center.crm.service.appuser.AppUserService;
import com.recruiting.center.crm.service.appuser.UserPositionService;
import com.recruiting.center.crm.service.appuser.WorkUnitService;
import com.recruiting.center.crm.utils.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationControllerService {
    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final WorkUnitService workUnitService;
    private final UserPositionService userPositionService;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Transactional
    public RegistrationResponseDto register(RegistrationRequestDto requestDto) {
        WorkUnit workUnit;
        UserPosition userPosition;
        try {
            workUnit = workUnitService.findByUnitName(requestDto.workUnit());
            userPosition = userPositionService.findByUnitName(requestDto.userPosition());
        } catch (Exception ex){
            log.error("RegistrationController: Non existing unit name or position");
            return new RegistrationResponseDto("", HttpStatus.OK);
        }
        if(userService.isExist(requestDto.username())){
            log.debug("RegistrationController: User has already exists");
            return new RegistrationResponseDto("", HttpStatus.OK);
        }
        AppUser build = AppUser.builder()
                .firstName(requestDto.firstName())
                .secondName(requestDto.secondName())
                .workUnit(workUnit)
                .userPosition(userPosition)
                .username(requestDto.username())
                .password(passwordEncoder.encode(requestDto.password()))
                .build();

        userService.save(build);
        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.username());
        String token = jwtUtils.generateToken(userDetails);
        return new RegistrationResponseDto(token, HttpStatus.OK);
    }
}
