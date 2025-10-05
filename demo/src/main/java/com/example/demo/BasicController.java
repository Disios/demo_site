package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BasicController {

    @Autowired
    private SiteUserRepository userRepository;

    // 초기 페이지 (회원가입)
    @GetMapping("/")
    public String showRegisterPage() {
        return "register.html";
    }

    // 회원가입 요청 처리
    @PostMapping("/register")
    public String processRegister(@RequestParam String username, @RequestParam String password) {
        // 새로운 User 객체 생성
        SiteUser newUser = new SiteUser(username, password);

        // UserRepository를 사용하여 데이터베이스에 저장하고,
        // 저장된 객체를 savedUser 변수에 담습니다.
        SiteUser savedUser = userRepository.save(newUser); // <-- 이 부분이 핵심입니다.

        System.out.println("--- 회원가입 데이터 저장 성공 ---");
        System.out.println("저장된 사용자 이름: " + savedUser.getUsername());
        System.out.println("저장된 비밀번호: " + savedUser.getPassword());
        System.out.println("----------------------------------------");

        // 가입 후 로그인 페이지로 리다이렉트
        return "redirect:/login.html";
    }

    // 로그인 페이지 보여주기
    @GetMapping("/login.html")
    public String getLoginPage() {
        return "login.html";
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
        SiteUser user = userRepository.findById(username).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/success.html";
        }
        return "redirect:/login.html";
    }

    // 성공/실패 페이지들
    @GetMapping("/success.html")
    public String showSuccessPage() {
        return "success.html";
    }

    @GetMapping("/fail.html")
    public String showFailPage() {
        return "fail.html";
    }
}