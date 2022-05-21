package com.example.toyproject.service;

import com.example.toyproject.entity.Location;
import com.example.toyproject.entity.Member;
import com.example.toyproject.entity.Pocket;
import com.example.toyproject.repository.PocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PocketService {

    private final PocketRepository pocketRepository;

    public List<Pocket> findByMemberIdAndLocation(Long id, Location order) {
        return pocketRepository.findByMemberIdAndLocation(id,order);
    }

    public List<Pocket> findByMemberAndLocation(Member member, Location order) {
        return pocketRepository.findByMemberAndLocation(member,order);
    }

    public Pocket save(Pocket pocket) {
        return pocketRepository.save(pocket);
    }

    public void deleteById(Long id) {
        pocketRepository.deleteById(id);
    }

    public void deleteByMemberAndLocation(Member member, Location pocket) {
        pocketRepository.deleteByMemberAndLocation(member,pocket);
    }

    public Optional<Pocket> findById(Long id) {
        return pocketRepository.findById(id);
    }

    public void deleteByMemberIdAndLocation(Long memberId, Location order) {
        pocketRepository.deleteByMemberIdAndLocation(memberId, order);
    }



    public List<Pocket> findAllByLocation(Location orderComplete) {
        return pocketRepository.findAllByLocation(orderComplete);
    }
}
