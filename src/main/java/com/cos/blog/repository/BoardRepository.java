package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	Optional<Board> findTopByOrderByIdDesc(); // 가장 최근 추가된 게시글 불러오기
}
