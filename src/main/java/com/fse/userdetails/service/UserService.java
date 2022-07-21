package com.fse.userdetails.service;

import com.fse.userdetails.exception.CommonInternalException;
import com.fse.userdetails.model.request.UserRequest;

import java.util.Map;

public interface UserService {
    Map<String, String> createUser(UserRequest request) throws CommonInternalException;

    Map<String, String> manualLogin(Map<String, String> request) throws CommonInternalException;

    Map<String, String> validateToken(String authToken) throws CommonInternalException;
}
