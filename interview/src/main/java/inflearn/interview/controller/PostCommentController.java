package inflearn.interview.controller;

import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{post_id}")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    //댓글 목록 조회
    @GetMapping("/comments")
    public List<PostCommentDTO> postComments(@PathVariable(name = "post_id") Long postId) {
        return postCommentService.getComments(postId);
    }

    //댓글 조회
    @GetMapping("/{comment_id}")
    public ResponseEntity<PostCommentDTO> postComment(@PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId) {
        PostCommentDTO comment = postCommentService.getComment(commentId);
        return ResponseEntity.ok(comment);
    }

    //댓글 작성
    @PostMapping("/submit")
    public ResponseEntity<PostCommentDTO> postCommentWrite(@PathVariable(name = "post_id") Long postId, @RequestBody @Validated(PostCommentDTO.valid1.class) PostCommentDTO postCommentDTO) {
        PostCommentDTO comment = postCommentService.createComment(postCommentDTO, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    //댓글 수정
    @PatchMapping("/{comment_id}")
    public void postCommentEdit(@PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId, @RequestBody @Validated(PostCommentDTO.valid1.class) PostCommentDTO postCommentDTO) {
        postCommentService.updateComment(postCommentDTO, commentId);
    }

    //댓글 삭제
    @DeleteMapping("/{comment_id}")
    public void postCommentDelete(@PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId, @RequestBody @Validated(PostCommentDTO.valid2.class) PostCommentDTO postCommentDTO) {
        postCommentService.deleteComment(commentId);
    }
}
