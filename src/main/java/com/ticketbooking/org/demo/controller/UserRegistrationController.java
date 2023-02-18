package com.ticketbooking.org.demo.controller;


import com.ticketbooking.org.demo.dto.user.UserDTO;
import com.ticketbooking.org.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController("/v1/user")
public class UserRegistrationController {
   private final UserService userService;

   @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO){
       if(userService.isUserPresent(userDTO)){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already exists");
       }

       return ResponseEntity.ok("Success");
   }

}
