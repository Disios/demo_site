package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, String> { // <-- 클래스명 변경
    // JpaRepository가 기본적인 CRUD(생성, 조회, 수정, 삭제) 기능을 자동으로 제공합니다.
}