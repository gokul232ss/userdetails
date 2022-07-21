package com.fse.userdetails.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.userdetails.config.JwtApplicationProperties;
import com.fse.userdetails.exception.CommonInternalException;
import com.fse.userdetails.model.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;

import javax.inject.Provider;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.time.Instant.now;
import static java.util.Date.from;

public final class CommonConstant {

    private static final Map<String, String> result = new HashMap<>();

    public static Map<String, String> getSuccessMapResponse() {
        result.put("message", "success");
        return result;
    }

    public static String getJWTToken(
            UserDetail userDetail, Provider<JwtBuilder> jwtBuilderProvider,
            ObjectMapper objectMapper, JwtApplicationProperties jwtProperties) {
        return jwtBuilderProvider.get()
                .setClaims(objectMapper.convertValue(userDetail, Map.class))
                .setExpiration(from(now().plus(jwtProperties.getExpTime())))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretCode())
                .compact();
    }

    public static UserDetail getJWTTokenUserDetails(
            String jwtToken, Provider<JwtParser> jwtParserProvider,
            ObjectMapper objectMapper, JwtApplicationProperties jwtProperties) throws CommonInternalException {
        Claims claims = jwtParserProvider.get()
                .setSigningKey(jwtProperties.getSecretCode())
                .parseClaimsJws(jwtToken)
                .getBody();
        Date expDateTime = new Date(claims.getExpiration().getTime());
        Date currentDateTime = new Date(System.currentTimeMillis());
        int expMill = currentDateTime.compareTo(expDateTime);
        if (expMill <= 0) {
            return objectMapper.convertValue(claims, UserDetail.class);
        } else {
            throw new CommonInternalException("Token was expired", HttpStatus.UNAUTHORIZED);
        }
    }
}
