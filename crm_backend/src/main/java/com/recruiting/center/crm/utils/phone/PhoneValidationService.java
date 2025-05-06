package com.recruiting.center.crm.utils.phone;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
public class PhoneValidationService {

    public String phoneNumberValidation(String phoneNumber){
        if(phoneNumber.startsWith("0")){
            return "+38" + phoneNumber;
        }
        if(phoneNumber.startsWith("380")){
            return "+" + phoneNumber;
        } else {
            return phoneNumber;
        }
    }
}
