package inflearn.interview.user.controller;

import inflearn.interview.common.aop.ValidateUserParam;
import inflearn.interview.notice.domain.NoticeDTO;
import inflearn.interview.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageController {

    private final MyPageService myPageService;

    @ValidateUserParam
    @GetMapping("/{user_id}/boards/reviews")
    public ResponseEntity<?> getReviewPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(myPageService.getMyPost(userId, "reviews"));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/boards/studies")
    public ResponseEntity<?> getStudyPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(myPageService.getMyPost(userId, "studies"));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/comments")
    public ResponseEntity<?> myComment(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(myPageService.getMyComment(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/feedbacks")
    public ResponseEntity<?> myfeedbacks(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(myPageService.getMyVideoComment(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/videos")
    public ResponseEntity<?> myVideo(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(myPageService.getMyVideo(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/notice")
    public ResponseEntity<?> getNotice(@PathVariable(name = "user_id") Long userId) {
        List<NoticeDTO> getNotice = myPageService.getMyNotice(userId);
        return ResponseEntity.ok(getNotice);
    }
}
