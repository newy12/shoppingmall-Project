package com.example.toyproject.entity;

import com.example.toyproject.repository.MemberRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userName;
    private String userPassword;
    private String address;
    private String addressDetail;
    private String signupPath;
    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();
    @Column(name = "auth")
    private String auth;
    @OneToMany(mappedBy = "member")
    private List<Reply> replyList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Pocket> pockets = new ArrayList<>();
/*    @OneToMany(mappedBy = "member")
    private List<Item> itemList = new ArrayList<>();*/
    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    @Enumerated(EnumType.STRING)
    private Role role;
/*    @OneToMany(mappedBy = "member")
    private List<Item> itemList = new ArrayList<>();*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }
    @Override
    public String getPassword() {

        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
