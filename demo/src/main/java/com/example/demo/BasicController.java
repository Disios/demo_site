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

    // 초기 페이지 (로그인)
    @GetMapping("/")
    public String showLoginPage() {
        return "login.html";
    }

    // 회원가입 페이지 보여주기
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register.html";
    }

    // 회원가입 요청 처리
    @PostMapping("/register")
    public String processRegister(@RequestParam String username, @RequestParam String password) {
        SiteUser newUser = new SiteUser(username, password);
        SiteUser savedUser = userRepository.save(newUser);

        System.out.println("--- 회원가입 데이터 저장 성공 ---");
        System.out.println("저장된 사용자 이름: " + savedUser.getUsername());
        System.out.println("저장된 비밀번호: " + savedUser.getPassword());
        System.out.println("----------------------------------------");

        return "redirect:/register_success.html";
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
        SiteUser user = userRepository.findById(username).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/success";
        }
        return "redirect:/login.html";
    }

    // 회원가입 성공 페이지 보여주기
    @GetMapping("/register_success")
    public String showRegisterSuccessPage() {
        return "register_success.html";
    }

    // 성공/실패 페이지들
    @GetMapping("/success")
    public String showSuccessPage() {
        return "success.html";
    }

    @GetMapping("/fail")
    public String showFailPage() {
        return "fail.html";
    }
}