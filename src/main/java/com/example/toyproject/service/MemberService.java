package com.example.toyproject.service;

import com.example.toyproject.entity.Member;
import com.example.toyproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public Member save(Member member) {
        return memberRepository.save(member);

    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
