package com.fse.userdetails.web;

import com.fse.userdetails.exception.CommonInternalException;
import com.fse.userdetails.model.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/user")
public interface UserInterface {

    @GetMapping("/getPing")
    ResponseEntity<String> getPing();

    @PostMapping("/createUser")
    ResponseEntity<Map<String, String>> createUser(@RequestBody UserRequest request) throws CommonInternalException;

    @PostMapping("/login")
    ResponseEntity<Map<String, String>> manualLogin(@RequestBody Map<String, String> request)
            throws CommonInternalException;

    @GetMapping("/validateToken")
    ResponseEntity<Map<String, String>> validateToken(@RequestHeader("auth-token") String authToken)
            throws CommonInternalException;

}
