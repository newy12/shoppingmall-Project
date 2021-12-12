package com.example.toyproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pocket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private String size;
    private String account;
    @Enumerated(EnumType.STRING)
    private Location location;

    @Builder
    public Pocket(Member member, Item item, String size, String account, Location location) {
        this.member = member;
        this.item = item;
        this.size = size;
        this.account = account;
        this.location = location;
    }
}
