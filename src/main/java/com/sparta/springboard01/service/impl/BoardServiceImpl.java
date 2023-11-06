package com.sparta.springboard01.service.impl;

import com.sparta.springboard01.domain.Board;
import com.sparta.springboard01.dto.BoardRegisterDTO;
import com.sparta.springboard01.dto.BoardRequestDTO;
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


    // TODO 업데이트를 할 때 더티체킹을 하기 때문에 레포지토리에서 세이브를 할 필요가 없다고 알고있습니다.
    //  근데 명시적으로 레포지토리를 호출하는 건 어떨지 궁금합니다. 업데이트를 제외한 jpa기능들이 레포지토리를 거쳐 적용되기 때문에 일관적으로 처리하는게 좋지 않을까 생각이 듭니다.
    //  컨트롤러 -> 서비스 -> 레포지토리의 흐름에서 이렇게 처리하는 게 자연스럽다고 느껴지는데 한 번 봐주실 수 있으실까요??
    /**
     * 엔티티 update메소드의 인자로 DTO를 받아 내부에서 dto.getTitle()로 변환해주는 방식은 조금 어색하다고 느꼈습니다. 도메인은 클래스 중에서도 수동적이고 정적이고 보수적인 클래스라고 생각했습니다.
     * dto가 어떻게 생겼는지 엔티티가 알 필요가 없다고 생각을 했습니다. 그래서 서비스에서 dto 내용을 꺼내어 직접 넣어주는 방식으로 했습니다.
     */
    @Override
    public BoardResponseDTO modify(Long bno, BoardRequestDTO boardRequestDTO) {
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

        if(board.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }


}
