package inflearn.interview.controller;

import inflearn.interview.domain.dto.MyPostDTO;
import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageController {

    private final UserService userService;

    @GetMapping("/{user_id}/boards")
    public List<MyPostDTO> myPost(@PathVariable(name = "user_id") Long userId) {
        return userService.getMyPost(userId);
    }

    @GetMapping("/{user_id}/comments")
    public List<PostCommentDTO> myComment(@PathVariable(name = "user_id") Long userId) {
        return userService.getMyComment(userId);
    }

    @GetMapping("/{user_id}/videos")
    public void myVideo(@PathVariable(name = "user_id") Long userId) {

    }
}
