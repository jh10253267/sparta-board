package com.sparta.springboard01.repository;

import com.sparta.springboard01.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


/**
 * - 선택한 게시글의 `제목`, `작성자명`, `작성 내용`을 수정할 수 있습니다.
 *     - 서버에 게시글 수정을 요청할 때 `비밀번호`를 함께 전달합니다.
 *     - 선택한 게시글의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 수정이 가능합니다.
 */
@SpringBootTest
@Log4j2
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void insertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i%10))
                    .password("password" + i)
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO" + result.getBno());
        });
    }

    @Test
    void selectTest() {
        Long bno = 1L;

        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    void readAllTest() {
        List<Board> boards = boardRepository.findAllByOrderByBnoDesc();
        boards.stream().forEach(board -> log.info(board));
    }
}