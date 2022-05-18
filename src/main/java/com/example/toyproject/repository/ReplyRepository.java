package com.example.toyproject.repository;

import com.example.toyproject.entity.Reply;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    List<Reply> findAllByItemId(Sort replyLocalDateTime, Long itemId);
}
