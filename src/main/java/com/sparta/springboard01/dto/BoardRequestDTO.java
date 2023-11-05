package com.sparta.springboard01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDTO {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private String password;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
