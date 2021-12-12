package com.example.toyproject.service;

import com.example.toyproject.entity.Member;


import com.example.toyproject.entity.Role;
import com.example.toyproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을수없습니다"+userId));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (member.getUserId().equals("admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(member.getUserId(), member.getUserPassword(), grantedAuthorities);
    }

}
