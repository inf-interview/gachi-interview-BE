package inflearn.interview.controller;

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

    @GetMapping("/{user_id}/boards/reviews")
    public ResponseEntity<?> getReviewPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyPost(userId, "reviews"));
    }

    @GetMapping("/{user_id}/boards/studies")
    public ResponseEntity<?> getStudyPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyPost(userId, "studies"));
    }

    @GetMapping("/{user_id}/comments")
    public ResponseEntity<?> myComment(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyComment(userId));
    }

    @GetMapping("/{user_id}/videos")
    public void myVideo(@PathVariable(name = "user_id") Long userId) {

    }

    @GetMapping("/{user_id}/notice")
    public ResponseEntity<?> getNotice(@PathVariable(name = "user_id") Long userId) {
        List<NoticeDTO> getNotice = userService.getMyNotice(userId);
        return ResponseEntity.ok(getNotice);
    }
}
