package inflearn.interview.videocomment.controller;

import inflearn.interview.common.aop.ValidateUser;
import inflearn.interview.postcomment.domain.PostCommentDTO;
import inflearn.interview.videocomment.controller.response.VideoCommentListResponse;
import inflearn.interview.videocomment.domain.VideoCommentCreate;
import inflearn.interview.videocomment.domain.VideoCommentDTO;
import inflearn.interview.videocomment.domain.VideoCommentDelete;
import inflearn.interview.videocomment.domain.VideoCommentUpdate;
import inflearn.interview.videocomment.service.VideoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video/{video_id}")
@RequiredArgsConstructor
public class VideoCommentController {

    private final VideoCommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<?> getVideoCommentsController(@PathVariable("video_id") Long videoId) {
        List<VideoCommentListResponse> response = commentService.getComments(videoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{comment_id}")
    public VideoCommentDTO getVideoCommentController(@PathVariable("comment_id") Long commentId) {
        return commentService.getComment(commentId);
    }

    @ValidateUser
    @PostMapping("/submit")
    public void addComment(@PathVariable("video_id") Long videoId, @RequestBody VideoCommentCreate videoCommentCreate) {
        commentService.create(videoId, videoCommentCreate);
    }

    @ValidateUser
    @PatchMapping("/comments/{comment_id}")
    public void updateComment(@PathVariable("comment_id") Long commentId, @RequestBody VideoCommentUpdate videoCommentUpdate) {
        commentService.update(videoCommentUpdate);
    }

    @ValidateUser
    @DeleteMapping("/comments/{comment_id}")
    public void deleteComment(@PathVariable("comment_id") Long commentId, @RequestBody VideoCommentDelete videoCommentDelete) {
        commentService.delete(videoCommentDelete);
    }

}