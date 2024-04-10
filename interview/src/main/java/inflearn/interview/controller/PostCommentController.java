package inflearn.interview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/{postId}")
@RequiredArgsConstructor
public class PostCommentController {

    //댓글 목록 조회
    @GetMapping("/comments")
    public void postComments(@PathVariable Long postId) {

    }

    //댓글 조회
    @GetMapping("/{commentId}")
    public void postComment(@PathVariable Long postId, @PathVariable Long commentId) {

    }

    //댓글 작성
    @PostMapping("/{commentId}")
    public void postCommentWrite(@PathVariable Long postId, @PathVariable Long commentId) {

    }

    //댓글 수정
    @PatchMapping("/{commentId}")
    public void postCommentEdit(@PathVariable Long postId, @PathVariable Long commentId) {

    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public void postCommentDelete(@PathVariable Long postId, @PathVariable Long commentId) {

    }
}
