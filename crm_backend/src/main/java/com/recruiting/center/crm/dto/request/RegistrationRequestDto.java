package com.recruiting.center.crm.dto.request;

public record RegistrationRequestDto (String firstName, String secondName,
                                      String workUnit, String userPosition,
                                      String username, String password){
}
