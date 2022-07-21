package com.fse.userdetails.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserRequest(
            Integer userId, String firstName,
            String lastName, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
