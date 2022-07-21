package com.fse.userdetails.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@Component
@Validated
@FieldDefaults(level = PRIVATE)
@ConfigurationProperties(prefix = "sso.jwt")
public class JwtApplicationProperties {
    public String secretCode;
    public Duration expTime;
}
