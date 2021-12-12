package com.example.toyproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Orders {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sizeOption;
    private LocalDateTime localDateTime;
    private String amountBuyItem;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

}
