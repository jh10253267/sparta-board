package com.sparta.springboard01.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    @Column(length=100, nullable = false)
    private String title;
    @Column(length=500, nullable = false)
    private String content;
    @Column(length = 50, nullable = false)
    private String writer;
    @Column(length = 50, nullable = false)
    private String password;

    public void update(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

}
