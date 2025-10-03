package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BasicController {

    // 처음 접속 시, 회원가입 페이지로 이동 (http://localhost:8080)
    @GetMapping("/")
    public String showRegisterPage() {
        return "register.html";
    }

    // 회원가입 페이지를 보여주는 GET 요청
    @GetMapping("/register")
    public String getRegisterPage() {
        return "register.html";
    }

    // 회원가입 요청을 처리하고, 로그인 페이지로 이동
    @PostMapping("/register")
    public String processRegister(@RequestParam String username, @RequestParam String password) {
        // 실제로는 이 곳에서 데이터베이스에 사용자 정보를 저장합니다.
        System.out.println("새로운 사용자 가입: " + username);
        // 가입 후 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }

    // 로그인 페이지를 보여주는 GET 요청
    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    // 로그인 요청을 처리하고, 결과 페이지로 이동
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
        // 간단한 예시: "admin"과 "1234"일 경우 성공으로 간주
        if ("admin".equals(username) && "1234".equals(password)) {
            // 로그인 성공 시 결과 페이지로 이동
            return "redirect:/success";
        }
        // 로그인 실패 시 다시 로그인 페이지로
        return "redirect:/login";
    }

    // 성공 결과 페이지를 보여주는 GET 요청
    @GetMapping("/success")
    public String showSuccessPage() {
        return "result success.html";
    }
}