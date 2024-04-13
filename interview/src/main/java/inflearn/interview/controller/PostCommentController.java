package inflearn.interview.controller;

import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{postId}")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    //댓글 목록 조회
    @GetMapping("/comments")
    public List<PostCommentDTO> postComments(@PathVariable Long postId) {
        return postCommentService.getComments(postId); // 안가져옴
    }

    //댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<PostCommentDTO> postComment(@PathVariable Long postId, @PathVariable Long commentId) {
        PostCommentDTO comment = postCommentService.getComment(commentId);
        return ResponseEntity.ok(comment);
    }

    //댓글 작성
    @PostMapping("/submit")
    public ResponseEntity<PostCommentDTO> postCommentWrite(@PathVariable Long postId, @RequestBody PostCommentDTO postCommentDTO) {
        PostCommentDTO comment = postCommentService.createComment(postCommentDTO, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    //댓글 수정
    @PatchMapping("/{commentId}")
    public void postCommentEdit(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody PostCommentDTO postCommentDTO) {
        postCommentService.updateComment(postCommentDTO, commentId);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public void postCommentDelete(@PathVariable Long postId, @PathVariable Long commentId) {
        postCommentService.deleteComment(commentId);
    }
}
