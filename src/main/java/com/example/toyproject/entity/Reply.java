package com.example.toyproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String replyId;
    private String replyPassword;
    private String orignalPassword;
    private String replyContent;
    @UpdateTimestamp
    private LocalDateTime replyLocalDateTime;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Reply(String replyId, String replyPassword, String replyContent, Member member, Item item,String orignalPassword) {
        this.replyId = replyId;
        this.replyPassword = replyPassword;
        this.replyContent = replyContent;
        this.member = member;
        this.item = item;
        this.orignalPassword = orignalPassword;
    }
}
