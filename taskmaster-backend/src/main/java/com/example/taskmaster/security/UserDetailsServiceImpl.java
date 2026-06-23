package com.example.taskmaster.security;

import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        private final UserRepository userRepository;

        public UserDetailsServiceImpl(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

                Set<GrantedAuthority> authorities = user.getRoles().stream()
                                // .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                .collect(Collectors.toSet());

                return org.springframework.security.core.userdetails.User
                                .withUsername(user.getEmail())
                                .password(user.getPassword())
                                .authorities(authorities)
                                .accountExpired(false)
                                .accountLocked(false)
                                .credentialsExpired(false)
                                .disabled(false)
                                .build();
        }
}