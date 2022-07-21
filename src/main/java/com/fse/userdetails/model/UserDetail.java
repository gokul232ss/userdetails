package com.fse.userdetails.model;

import com.fse.userdetails.model.request.UserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserDetail(UserRequest request) {
        if (request.getUserId() != null && request.getUserId() != 0) {
            this.userId = request.getUserId();
        }
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }
}
