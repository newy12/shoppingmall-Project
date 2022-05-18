package com.example.toyproject.service;

import com.example.toyproject.entity.Reply;
import com.example.toyproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }

    public List<Reply> findAllByItemId(Long itemId) {
        return replyRepository.findAllByItemId(Sort.by(Sort.Direction.DESC,"replyLocalDateTime"),itemId);
    }
}
