package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.attribute.UserPrincipal;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username,username).orElseThrow(()->new UsernameNotFoundException("Khong tim thay user"));
        return UsePrincipal.create(user);

    }
    @Transactional
    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Khong tim thay id"));
        return UsePrincipal.create(user);
    }
}
