package com.ticketbooking.org.demo.dto.user;


import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;

    private String password;

    private String confirmPassword;

}