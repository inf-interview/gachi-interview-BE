package inflearn.interview.postcomment.controller;

import inflearn.interview.postcomment.controller.response.PostCommentCreateResponse;
import inflearn.interview.postcomment.controller.response.PostCommentListResponse;
import inflearn.interview.postcomment.domain.PostCommentCreate;
import inflearn.interview.postcomment.domain.PostCommentDelete;
import inflearn.interview.postcomment.domain.PostCommentUpdate;
import inflearn.interview.postcomment.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{post_id}")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    //댓글 목록 조회
    @GetMapping("/comments")
    public ResponseEntity<List<PostCommentListResponse>> postComments(@PathVariable(name = "post_id") Long postId) {
        return ResponseEntity.ok(postCommentService.getComments(postId));
    }

    //댓글 작성
    @PostMapping("/submit")
    public ResponseEntity<PostCommentCreateResponse> postCommentWrite(@PathVariable(name = "post_id") Long postId, @RequestBody PostCommentCreate postCommentCreate) {
        PostCommentCreateResponse response = postCommentService.createComment(postCommentCreate, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //댓글 수정
    @PatchMapping("/comments/{comment_id}")
    public ResponseEntity<?> postCommentEdit(@PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId, @RequestBody PostCommentUpdate postCommentUpdate) {
        postCommentService.updateComment(postCommentUpdate);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //댓글 삭제
    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<?> postCommentDelete(@PathVariable(name = "post_id") Long postId, @PathVariable(name = "comment_id") Long commentId, @RequestBody PostCommentDelete postCommentDelete) {
        postCommentService.deleteComment(postCommentDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
