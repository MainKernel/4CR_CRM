package com.recruiting.center.crm.dto.request;

import java.time.LocalDate;


public record CandidateAddRequestDto(
        String name,
        String surname,
        String middleName,
        Long status,
        String phone,
        Long dutyType,
        Long unit,
        Long position,
        LocalDate rlDate,
        String recruiter,
        String curator,
        String escort,
        String psychologicalTest,
        LocalDate unitOrder,
        String unitNumber,
        String territoryRecord,
        LocalDate order,
        String orderNumber
){
}
