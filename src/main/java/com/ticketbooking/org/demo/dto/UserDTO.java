package com.ticketbooking.org.demo.dto;


import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;

    private String password;

    private String confirmPassword;

}