package com.fse.userdetails.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.userdetails.common.CommonConstant;
import com.fse.userdetails.config.JwtApplicationProperties;
import com.fse.userdetails.exception.CommonInternalException;
import com.fse.userdetails.model.UserDetail;
import com.fse.userdetails.model.request.UserRequest;
import com.fse.userdetails.repository.UserRepository;
import com.fse.userdetails.service.UserService;
import com.google.common.base.Optional;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    Provider<JwtBuilder> jwtBuilderProvider;
    @Autowired
    Provider<JwtParser> jwtParserProvider;
    @Autowired
    private UserRepository repository;
    @Autowired
    private ObjectMapper ob;
    @Autowired
    private JwtApplicationProperties properties;

    @Override
    public Map<String, String> createUser(UserRequest request) throws CommonInternalException {
        Optional<UserDetail> user = repository.findByEmail(request.getEmail());
        if (!user.isPresent()) {
            repository.saveAndFlush(new UserDetail(request));
            return CommonConstant.getSuccessMapResponse();
        } else {
            throw new CommonInternalException("User already exist", HttpStatus.CONFLICT);
        }
    }

    @Override
    public Map<String, String> manualLogin(Map<String, String> request)
            throws CommonInternalException {
        Optional<UserDetail> user = repository.findByEmail(request.get("userName"));
        if (user.isPresent()) {
            if (user.get().getPassword().equals(request.get("password"))) {
                Map<String, String> result = CommonConstant.getSuccessMapResponse();
                result.put("auth-token",
                        CommonConstant.getJWTToken(user.get(), jwtBuilderProvider, ob, properties));
                return result;
            } else {
                throw new CommonInternalException("Entered wrong password", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new CommonInternalException("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Map<String, String> validateToken(String authToken) throws CommonInternalException {
        UserDetail user = CommonConstant.getJWTTokenUserDetails(authToken, jwtParserProvider, ob, properties);
        Map<String, String> mp = new HashMap<>();
        mp.put("userName", user.getEmail());
        mp.put("password", user.getPassword());
        return manualLogin(mp);
    }
}
