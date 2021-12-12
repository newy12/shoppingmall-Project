package com.example.toyproject.repository;

import com.example.toyproject.entity.Item;
import com.example.toyproject.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByCategory(String category);
}
