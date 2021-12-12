package com.example.toyproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {

    @Id @GeneratedValue
    private Long id;
    private String itemName;
    private String itemPrice;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="file_id")
    private Files file;
/*    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;*/
    @OneToMany(mappedBy = "item")
    private List<Reply> replyList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<Pocket>  pockets = new ArrayList<>();
    private String category;

}
