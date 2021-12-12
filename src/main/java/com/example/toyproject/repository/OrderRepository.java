package com.example.toyproject.repository;

import com.example.toyproject.entity.Member;
import com.example.toyproject.entity.Orders;
import com.example.toyproject.entity.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByMemberId(Long memberId);

    @Transactional
    void deleteByMember(Member member);

    @Transactional
    List<Orders> save(List<Pocket> pockets);
}
