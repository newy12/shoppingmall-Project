package com.example.toyproject.repository;

import com.example.toyproject.entity.Item;
import com.example.toyproject.entity.ItemType;
import com.example.toyproject.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByItemType(ItemType itemType);


    List<Item> findByItemNameContaining(String keyword);
}
