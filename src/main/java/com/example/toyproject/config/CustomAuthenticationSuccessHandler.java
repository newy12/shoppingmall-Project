package com.example.toyproject.config;

import com.example.toyproject.entity.Member;
import com.example.toyproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 시 UserDetails에 담긴 사용자 정보 => 아이디 = admin 이면 /adminPage  아이디 = admin가 아니면 / 이동
        String user = authentication.getName();
        String UserIdWord = user.toLowerCase();
        if(UserIdWord.equals("admin")){
        response.sendRedirect("/adminPage");
        }else{
            response.sendRedirect("/");
        }
        //good
    }



}
