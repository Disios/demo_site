package com.example.demo;

// 이 클래스는 실제 Spring Security의 사용자 정보를 대신하여
// 임시 작성자 이름을 제공하는 역할을 합니다.
public class SessionUser {
    public static String getLoggedInUsername() {
        // 모든 글과 댓글의 작성자는 임시로 'Tester'로 표시됩니다.
        return "Tester";
    }
}
