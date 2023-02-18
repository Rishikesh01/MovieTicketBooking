package com.ticketbooking.org.demo.service;


import com.ticketbooking.org.demo.dto.user.UserDTO;
import com.ticketbooking.org.demo.model.User;
import com.ticketbooking.org.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userDetailsRepository.findByEmail(username);
        if (result.isPresent())
            return result.get();
        throw new UsernameNotFoundException("user with email " + username + " not found");
    }

    public void   save(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        userDetailsRepository.save(user);
    }

    public boolean isUserPresent(UserDTO userDTO){
        Optional<User> result = userDetailsRepository.findByEmail(userDTO.getEmail());
        return result.isPresent();
    }
}