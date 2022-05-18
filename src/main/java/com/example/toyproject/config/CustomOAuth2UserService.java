package com.example.toyproject.config;

import com.example.toyproject.entity.Member;
import com.example.toyproject.entity.Role;
import com.example.toyproject.entity.User;
import com.example.toyproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)/ 네이버 카카오 지원 x
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 데이터를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //로그인 한 유저 정보
        Member user = saveOrUpdate(attributes);

        //httpSession의 유저 속성을 설정
/*        httpSession.setAttribute("userSession", new SessionUser(user));*/
        httpSession.setAttribute("userName", user.getKakaoEmail());
        httpSession.setAttribute("name",user.getUsername());

        // 로그인한 유저를 리턴함
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    //User 저장하고 이미 있는 데이터면 Update
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Optional<Member> user = memberRepository.findByKakaoEmail(attributes.getEmail());
        if (user.isPresent()) {
            user.get().setOrders(null);
            user.get().setReplyList(null);
            user.get().setPockets(null);
            user.get().setUserName(attributes.getEmail());
            return memberRepository.save(user.get());
        }else{
            Member member = new Member();
            member.setUserId(attributes.getEmail());
            member.setUserName(attributes.getEmail());
            member.setRole(Role.MEMBER);
            member.setPassword(null);
            member.setAddress(null);
            member.setAddressDetail(null);
            member.setKakaoEmail(attributes.getEmail());
            member.setOrders(null);
            member.setReplyList(null);
            member.setPockets(null);
            return memberRepository.save(member);
        }
        /*User user2 = userRepository.findAllByEmail(attributes.getEmail());
        Optional<User> OptUser = userRepository.findByEmail(attributes.getEmail());
        if (OptUser.isPresent()) {
            User user = OptUser.get();
            user.setName(attributes.getName());
            user.setPicture(attributes.getPicture());
            return userRepository.save(user);
        }else{
            User user = new User();
            user.setName(attributes.getName());
            user.setEmail(attributes.getEmail());
            user.setPicture(attributes.getPicture());
            user.setRole(attributes.toEntity().getRole());
            return userRepository.save(user);
        }*/
    }
}

