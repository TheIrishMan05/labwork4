package se.ifmo.lab4backend.models;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String username;
    private String password;

}
