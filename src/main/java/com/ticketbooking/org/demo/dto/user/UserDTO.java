package com.ticketbooking.org.demo.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;

    private String password;

    @JsonProperty("confirm_password")
    private String confirmPassword;

}