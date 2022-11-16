package com.gangwondog.openAPI.config.security;

import com.gangwondog.openAPI.domain.login.LoginRepository;
import com.gangwondog.openAPI.domain.user.UserEntityP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(UserEntityP userEntityP) {
        System.out.println("member.getAuthority().toString() = " + userEntityP.getAuthority().toString());
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userEntityP.getAuthority().toString());
        System.out.println("grantedAuthority = " + grantedAuthority);
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(userEntityP.getId()),
                userEntityP.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
