package com.example.toyproject.service;

import com.example.toyproject.entity.Member;
import com.example.toyproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public void sendMail(User user , String email) {

        ArrayList<String> toUserList = new ArrayList<>();

        toUserList.add(email);

        int toUserSize = toUserList.size();

        SimpleMailMessage simpleMailMessage =  new SimpleMailMessage();
        simpleMailMessage.setTo(toUserList.toArray(new String[toUserSize]));


        simpleMailMessage.setSubject("비밀번호 초기화 안내 메일");

        String generatedString = RandomStringUtils.randomAlphanumeric(10);
        Member member = memberRepository.findByUserId(user.getUsername()).get();
        member.setPassword(passwordEncoder.encode(generatedString));

        memberRepository.save(member);
        simpleMailMessage.setText("비밀번호가 초기화 되었습니다. =>"+"[ "+generatedString+" ]");

        javaMailSender.send(simpleMailMessage);

    }

}
