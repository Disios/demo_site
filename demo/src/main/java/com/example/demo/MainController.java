package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/main")
    public String showMainPage(Model model) {
        // 최신 게시글 10개를 불러와 모델에 추가
        List<Post> posts = postRepository.findTop10ByOrderByIdDesc();
        model.addAttribute("posts", posts);
        return "main.html";
    }

    @GetMapping("/write")
    public String showWritePage() {
        return "write.html";
    }

    @PostMapping("/write")
    public String savePost(@RequestParam String title, @RequestParam String content) {
        Post newPost = new Post(title, content);
        Post savedPost = postRepository.save(newPost);
        // 저장된 게시글의 ID를 사용하여 해당 페이지로 리다이렉트
        return "redirect:/post/" + savedPost.getId();
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            model.addAttribute("post", post);
            return "view_post.html";
        }
        // 게시글이 없으면 에러 페이지로 이동
        return "redirect:/fail";
    }
}