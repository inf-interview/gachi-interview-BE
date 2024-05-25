package inflearn.interview.controller;

import inflearn.interview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageController {

    private final UserService userService;

    @GetMapping("/{user_id}/boards")
    public ResponseEntity<?> myPost(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyPost(userId));
    }

    @GetMapping("/{user_id}/comments")
    public ResponseEntity<?> myComment(@PathVariable(name = "user_id") Long userId) {
        return ResponseEntity.ok(userService.getMyComment(userId));
    }

    @GetMapping("/{user_id}/videos")
    public void myVideo(@PathVariable(name = "user_id") Long userId) {

    }
}
