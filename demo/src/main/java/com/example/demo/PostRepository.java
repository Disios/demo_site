package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 게시글 ID를 내림차순으로 정렬하여 최대 10개까지 가져옵니다.
    List<Post> findTop10ByOrderByIdDesc();
}