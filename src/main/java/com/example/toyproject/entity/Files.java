package com.example.toyproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;     // 파일 식별 아이디
    private String filename;     //파일명
    private String fileOriName;  //파일실제명
    private String fileurl;     //파일url
    @JsonIgnore
    @OneToOne(mappedBy = "file",cascade = CascadeType.ALL)
    private Item item;
}
