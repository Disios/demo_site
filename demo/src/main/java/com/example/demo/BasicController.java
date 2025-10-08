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

        return "redirect:/register_success";
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
        // 1. 사용자 ID로 데이터베이스에서 사용자 정보를 조회
        SiteUser user = userRepository.findById(username).orElse(null);

        // 2. 사용자가 존재하고 비밀번호가 일치하면
        if (user != null && user.getPassword().equals(password)) {

            // 로그인 성공 시 메인 커뮤니티 페이지로 이동하도록 수정
            return "redirect:/main";

        }
        // 3. 실패하면 로그인 페이지로 다시 리다이렉트
        return "redirect:/fail.html"; // fail.html 대신 login.html로 리다이렉트하는 것이 일반적이지만, 현재 fail.html로 설정되어 있습니다.
    }

    // 회원가입 성공 페이지 보여주기
    @GetMapping("/register_success")
    public String showRegisterSuccessPage() {
        return "register_success.html";
    }

    @GetMapping("/fail")
    public String showFailPage() {
        return "fail.html";
    }
}