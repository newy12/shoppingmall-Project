package com.example.toyproject.service;

import com.example.toyproject.entity.Item;
import com.example.toyproject.entity.ItemType;
import com.example.toyproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;



    public List<Item> findByCategory(ItemType itemType) {
        return itemRepository.findByItemType(itemType);
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
