package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private static final int PAGE_SIZE = 10;

    // 메인 페이지 (게시글 목록 및 페이지네이션)
    @GetMapping("/main")
    public String showMainPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());
        Page<Post> postPage = postRepository.findAll(pageable);

        model.addAttribute("postPage", postPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());

        return "main.html";
    }

    @GetMapping("/write")
    public String showWritePage() {
        return "write.html";
    }

    // 글 작성 및 작성자 이름 자동 저장
    @PostMapping("/write")
    public String savePost(@RequestParam String title,
                           @RequestParam String content) {

        // SessionUser를 사용하여 작성자 이름 참조 (오류 발생 지점)
        String authorName = SessionUser.getLoggedInUsername();

        Post newPost = new Post(title, content, authorName);
        Post savedPost = postRepository.save(newPost);

        return "redirect:/post/" + savedPost.getId();
    }

    // 게시글 상세 보기
    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            model.addAttribute("post", post);
            model.addAttribute("comments", post.getComments());
            return "view_post.html";
        }
        return "redirect:/fail";
    }

    // 댓글 작성 처리
    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content) {

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "redirect:/fail";
        }

        // SessionUser를 사용하여 작성자 이름 참조 (오류 발생 지점)
        String authorName = SessionUser.getLoggedInUsername();

        Comment newComment = new Comment(content, authorName, post);
        commentRepository.save(newComment);

        return "redirect:/post/" + postId;
    }
}
