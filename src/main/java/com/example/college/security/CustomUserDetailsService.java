package com.example.college.security;

import com.example.college.model.User;
import com.example.college.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Collection<? extends GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()));
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), auths);
    }
}
