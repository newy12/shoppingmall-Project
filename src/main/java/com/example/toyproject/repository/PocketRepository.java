package com.example.toyproject.repository;


import com.example.toyproject.entity.Location;
import com.example.toyproject.entity.Member;
import com.example.toyproject.entity.Orders;
import com.example.toyproject.entity.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PocketRepository extends JpaRepository<Pocket,Long> {

    List<Pocket> findByMemberId(Long memberId);

    List<Pocket> findByMemberIdAndLocation(Long memberId,Location location);

    List<Pocket> findByLocation(Location location);

    List<Pocket> findByMemberAndLocation(Member member,Location location);

    @Transactional
    void deleteByMemberAndLocation(Member member,Location location);



}
