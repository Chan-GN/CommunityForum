package com.me.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.me.community.auth.Authority;
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
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name; // 이름
    private String password;
    private int point; // 포인트
    private String nickname; // 닉네임
    private String career; // 경력
    private String introduce; // 소개글

    // 일대다, 필요한가?
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Article> postArticles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public User(String name, String password, Authority authority) {
        this.name = name;
        this.password = password;
        this.authority = authority;
    }
}
