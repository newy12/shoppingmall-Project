package com.example.toyproject.service;

import com.example.toyproject.entity.Reply;
import com.example.toyproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }
}
