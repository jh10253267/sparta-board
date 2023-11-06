package com.sparta.springboard01.service.impl;

import com.sparta.springboard01.domain.Board;
import com.sparta.springboard01.dto.BoardRegisterDTO;
import com.sparta.springboard01.dto.BoardModifyDTO;
import com.sparta.springboard01.dto.BoardResponseDTO;
import com.sparta.springboard01.repository.BoardRepository;
import com.sparta.springboard01.service.BoardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;


    @Override
    public BoardResponseDTO register(BoardRegisterDTO boardRegisterDTO) {
        Board board = modelMapper.map(boardRegisterDTO, Board.class);
        boardRepository.save(board);
        BoardResponseDTO boardResponseDTO = modelMapper.map(board, BoardResponseDTO.class);
        return boardResponseDTO;
    }

    @Override
    public BoardResponseDTO modify(Long bno, BoardModifyDTO boardRequestDTO) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        String modTitle = boardRequestDTO.getTitle();
        String modContent = boardRequestDTO.getContent();
        String modWriter = boardRequestDTO.getWriter();

        board.update(modTitle, modContent, modWriter);
        boardRepository.save(board);

        BoardResponseDTO boardResponseDTO = modelMapper.map(board, BoardResponseDTO.class);
        return boardResponseDTO;

    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public BoardResponseDTO readOne(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        BoardResponseDTO boardResponseDTO = modelMapper.map(board, BoardResponseDTO.class);
        return boardResponseDTO;
    }

    @Override
    public List<BoardResponseDTO> readAll() {
        List<Board> boards = boardRepository.findAllByOrderByBnoDesc();

        List<BoardResponseDTO> boardResponseDTOS = boards.stream()
                .map(board -> modelMapper.map(board, BoardResponseDTO.class))
                .collect(Collectors.toList());

        return boardResponseDTOS;

    }

    @Override
    public boolean checkPassword(Long bno, String password) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        if (board.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
