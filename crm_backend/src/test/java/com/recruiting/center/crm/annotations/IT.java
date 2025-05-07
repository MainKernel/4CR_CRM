package com.recruiting.center.crm.annotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@PropertySource("classpath:application-test.property")
public @interface IT{
}
