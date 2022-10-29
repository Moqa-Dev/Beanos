package com.moqa.beanos.infrastructure;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationProperties {

    @Value("${spring.application.name}")
    private String applicationName;
}
