package inflearn.interview.user.controller;

import inflearn.interview.common.aop.ValidateUserParam;
import inflearn.interview.notice.controller.response.NoticeResponse;
import inflearn.interview.post.service.PostService;
import inflearn.interview.postcomment.service.PostCommentService;
import inflearn.interview.user.service.UserService;
import inflearn.interview.video.service.VideoService;
import inflearn.interview.videocomment.service.VideoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageController {

    private final PostService postService;
    private final VideoService videoService;
    private final PostCommentService postCommentService;
    private final VideoCommentService videoCommentService;
    private final UserService userService;

    @ValidateUserParam
    @GetMapping("/{user_id}/boards/reviews")
    public ResponseEntity<?> getReviewPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(postService.getMyPost(userId, "reviews"));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/boards/studies")
    public ResponseEntity<?> getStudyPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(postService.getMyPost(userId, "studies"));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/comments")
    public ResponseEntity<?> myComment(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(postCommentService.getMyComment(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/videos")
    public ResponseEntity<?> myVideo(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(videoService.getMyVideo(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/feedbacks")
    public ResponseEntity<?> myfeedbacks(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(videoCommentService.getMyVideoComment(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/notice")
    public ResponseEntity<?> getNotice(@PathVariable(name = "user_id") Long userId) {
        List<NoticeResponse> noticeResponse = userService.getMyNotice(userId);
        return ResponseEntity.ok(noticeResponse);
    }
}
