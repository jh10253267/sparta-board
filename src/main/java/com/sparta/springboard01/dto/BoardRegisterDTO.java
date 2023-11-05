package com.sparta.springboard01.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRegisterDTO {
    private String title;
    private String content;
    private String writer;
    private String password;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
