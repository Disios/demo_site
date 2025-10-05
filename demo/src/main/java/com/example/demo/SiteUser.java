package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SiteUser { // <-- 클래스명 변경
    @Id
    private String username;
    private String password;

    // 기본 생성자 (JPA 필수)
    public SiteUser() {}

    public SiteUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}