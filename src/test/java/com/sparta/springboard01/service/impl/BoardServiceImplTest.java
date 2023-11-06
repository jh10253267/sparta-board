package com.sparta.springboard01.service.impl;

import com.sparta.springboard01.dto.BoardRegisterDTO;
import com.sparta.springboard01.dto.BoardResponseDTO;
import com.sparta.springboard01.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
@Log4j2
class BoardServiceImplTest {
    @Autowired
    BoardService boardService;

    @Test
    void registerTest() {
        log.info(boardService.getClass().getName());
        BoardRegisterDTO boardRegisterDTO = BoardRegisterDTO.builder()
                .title("sample test")
                .content("content test")
                .writer("writer test")
                .password("password test")
                .build();
        BoardResponseDTO boardResponseDTO = boardService.register(boardRegisterDTO);
        log.info(boardResponseDTO);
    }

    @Test
    void readOneTest() {
        Long bno = 1L;

        BoardResponseDTO boardResponseDTO = boardService.readOne(bno);
        log.info(boardResponseDTO);
    }

    @Test
    void readAllTest() {
        List<BoardResponseDTO> boardResponseDTOList = boardService.readAll();
        boardResponseDTOList.stream()
                .forEach(boardResponseDTO -> log.info(boardResponseDTO));



    }
}