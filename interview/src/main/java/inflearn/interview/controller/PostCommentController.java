package inflearn.interview.controller;

import inflearn.interview.aop.ValidateUser;
import inflearn.interview.domain.dto.ErrorResponse;
import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.exception.RequestDeniedException;
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
    @ValidateUser
    @PostMapping("/submit")
    public ResponseEntity<PostCommentDTO> postCommentWrite(@PathVariable(name = "post_id") Long postId, @RequestBody @Validated(PostCommentDTO.create.class) PostCommentDTO postCommentDTO) {
        PostCommentDTO comment = postCommentService.createComment(postCommentDTO, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    //댓글 수정
    @ValidateUser
    @PatchMapping("/comments/{comment_id}")
    public ResponseEntity<?> postCommentEdit(@PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId, @RequestBody @Validated(PostCommentDTO.update.class) PostCommentDTO postCommentDTO) {
        postCommentService.updateComment(postId, commentId, postCommentDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //댓글 삭제
    @ValidateUser
    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<?> postCommentDelete(@PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId, @RequestBody @Validated(PostCommentDTO.delete.class) PostCommentDTO postCommentDTO) {
        postCommentService.deleteComment(postId, commentId, postCommentDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
