package com.example.toyproject.repository;


import com.example.toyproject.entity.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByUserId(String userId);

}
