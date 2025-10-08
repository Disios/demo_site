package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

// 세션 처리를 위해 가상의 유저 이름을 가져오는 헬퍼 클래스 가정
// 실제로는 로그인 세션에서 가져와야 합니다.
class SessionUser {
    public static String getLoggedInUsername() {
        // 실제 프로젝트에서는 Spring Security Context에서 가져옵니다.
        // 여기서는 임시로 "loggedInUser"를 반환하거나, BasicController에서 세션에 저장한 값을 사용합니다.
        return "loggedInUser";
    }
}


@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    // 페이지 크기 설정 (요청대로 10개)
    private static final int PAGE_SIZE = 10;

    // 메인 페이지 (게시글 목록 및 페이지네이션)
    @GetMapping("/main")
    public String showMainPage(@RequestParam(defaultValue = "0") int page, Model model) {
        // ID 기준 내림차순 정렬 (최신 글 먼저)
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());
        Page<Post> postPage = postRepository.findAll(pageable);

        model.addAttribute("postPage", postPage);
        model.addAttribute("currentPage", page);

        // 총 페이지 수 (템플릿에서 페이지 버튼을 만드는 데 사용)
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

        // 현재 로그인 사용자 이름을 가정하여 가져옴
        String authorName = SessionUser.getLoggedInUsername();

        Post newPost = new Post(title, content, authorName);
        Post savedPost = postRepository.save(newPost);

        // 저장된 게시글의 ID를 사용하여 해당 페이지로 리다이렉트
        return "redirect:/post/" + savedPost.getId();
    }

    // 게시글 상세 보기 (작성자 표시 및 댓글 목록/폼 포함)
    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            model.addAttribute("post", post);
            // 댓글 목록은 Post 엔티티에 포함된 getComments()를 통해 가져옴
            model.addAttribute("comments", post.getComments());
            return "view_post.html";
        }
        return "redirect:/fail"; // 게시글이 없으면 실패 페이지로 리다이렉트
    }

    // 댓글 작성 처리
    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content) {

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "redirect:/fail";
        }

        // 현재 로그인 사용자 이름을 가정하여 가져옴
        String authorName = SessionUser.getLoggedInUsername();

        Comment newComment = new Comment(content, authorName, post);
        commentRepository.save(newComment);

        // 댓글 작성 후 해당 게시글 페이지로 리다이렉트
        return "redirect:/post/" + postId;
    }
}
