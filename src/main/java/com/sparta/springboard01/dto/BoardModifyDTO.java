package com.sparta.springboard01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardModifyDTO {
    private String title;
    private String content;
    private String writer;
    private String password;
}
