package com.example.toyproject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class test implements Serializable {

    private String name = "김영재";
    private String email;
    private int age;

    public test(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    @Override
    public String toString(){
        return String.format("Member{name='%s', email='%s', age='%s'}", name, email, age);
    }
}
