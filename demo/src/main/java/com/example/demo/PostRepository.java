package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 페이지네이션을 위해 Pageable 객체를 인자로 받습니다.
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);
}
