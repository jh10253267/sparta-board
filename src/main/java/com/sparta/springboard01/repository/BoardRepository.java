package com.sparta.springboard01.repository;

import com.sparta.springboard01.domain.Board;
import com.sparta.springboard01.dto.BoardResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByBnoDesc();
}
