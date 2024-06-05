package inflearn.interview.controller;

import inflearn.interview.aop.ValidateUserParam;
import inflearn.interview.domain.dto.NoticeDTO;
import inflearn.interview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageController {

    private final UserService userService;

    @ValidateUserParam
    @GetMapping("/{user_id}/boards/reviews")
    public ResponseEntity<?> getReviewPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyPost(userId, "reviews"));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/boards/studies")
    public ResponseEntity<?> getStudyPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyPost(userId, "studies"));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/comments")
    public ResponseEntity<?> myComment(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyComment(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/feedbacks")
    public ResponseEntity<?> myfeedbacks(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyVideoComment(userId));
    }

    @ValidateUserParam
    @GetMapping("/{user_id}/videos")
    public void myVideo(@PathVariable(name = "user_id") Long userId) {

    }

    @ValidateUserParam
    @GetMapping("/{user_id}/notice")
    public ResponseEntity<?> getNotice(@PathVariable(name = "user_id") Long userId) {
        List<NoticeDTO> getNotice = userService.getMyNotice(userId);
        return ResponseEntity.ok(getNotice);
    }
}
