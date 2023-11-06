package com.sparta.springboard01.controller;

import com.sparta.springboard01.dto.BoardPassword;
import com.sparta.springboard01.dto.BoardRegisterDTO;
import com.sparta.springboard01.dto.BoardModifyDTO;
import com.sparta.springboard01.dto.BoardResponseDTO;
import com.sparta.springboard01.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Log4j2
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping("/")
    public Map<String, Object> listAll() {
        Map<String, Object> map = new HashMap<>();
        List<BoardResponseDTO> boards = boardService.readAll();
        map.put("boards", boards);
        return map;
    }

    //단일 건 조회
    @GetMapping("/{bno}")
    public ResponseEntity<Map<String, Object>> list(@PathVariable("bno") Long bno) {
        Map<String, Object> map = new HashMap<>();

        try {
            BoardResponseDTO boardResponseDTO = boardService.readOne(bno);
            map.put("board", boardResponseDTO);
        } catch(Exception e) {
            map.put("Error", "해당 게시물은 존재하지 않습니다.");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(map);
    }

    //게시글 작성
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> register(@RequestBody BoardRegisterDTO boardRegisterDTO) {
        Map<String, Object> map = new HashMap<>();

        try {
            BoardResponseDTO boardResponseDTO = boardService.register(boardRegisterDTO);
            map.put("board", boardResponseDTO);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            map.put("Error", "잘못된 양식입니다.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

    }

    //게시글 수정
    @PutMapping("/{bno}")
    public ResponseEntity<Map<String, Object>> modify(@PathVariable("bno") Long bno, @RequestBody BoardModifyDTO boardRequestDTO) {
        Map<String, Object> map = new HashMap<>();
        String password = boardRequestDTO.getPassword();
        try {
            boolean isEqual = boardService.checkPassword(bno, password);

            if (isEqual) {
                BoardResponseDTO boardResponseDTO = boardService.modify(bno, boardRequestDTO);
                map.put("updated board", boardResponseDTO);
                return ResponseEntity.ok(map);
            } else {
                map.put("Error", "비밀번호가 일치하지 않습니다.");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            map.put("Error", "존재하지 않는 게시물입니다.");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    //게시글 삭제
    //비밀번호만 따로 받기.
    @DeleteMapping("/{bno}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable("bno") Long bno, @RequestBody BoardPassword boardPassword) {
        Map<String, Object> map = new HashMap<>();
        String password = boardPassword.getPassword();
        boolean isEqual = boardService.checkPassword(bno, password);

        if(isEqual) {
            boardService.remove(bno);
            map.put("Success", "삭제가 성공적으로 완료되었습니다.");
            return ResponseEntity.ok(map);
        } else {
            map.put("Error", "비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
    }


}
