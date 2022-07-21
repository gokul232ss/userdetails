package com.fse.userdetails.web.impl;

import com.fse.userdetails.exception.CommonInternalException;
import com.fse.userdetails.model.request.UserRequest;
import com.fse.userdetails.service.UserService;
import com.fse.userdetails.web.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController implements UserInterface {

    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<String> getPing() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> createUser(UserRequest request) throws CommonInternalException {
        return new ResponseEntity<>(service.createUser(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, String>> manualLogin(Map<String, String> request) throws CommonInternalException {
        return new ResponseEntity<>(service.manualLogin(request), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> validateToken(String authToken) throws CommonInternalException {
        return new ResponseEntity<>(service.validateToken(authToken), HttpStatus.OK);
    }
}
