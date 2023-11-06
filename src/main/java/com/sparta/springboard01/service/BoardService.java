package com.sparta.springboard01.service;

import com.sparta.springboard01.dto.BoardRegisterDTO;
import com.sparta.springboard01.dto.BoardRequestDTO;
import com.sparta.springboard01.dto.BoardResponseDTO;

import java.util.List;

public interface BoardService {
    BoardResponseDTO register(BoardRegisterDTO boardRegisterDTO);
    BoardResponseDTO modify(Long bno, BoardRequestDTO boardRequestDTO);
    void remove(Long bno);
    BoardResponseDTO readOne(Long bno);
    List<BoardResponseDTO> readAll();
    boolean checkPassword(Long bno, String password);

}
