package inflearn.interview.controller;

import inflearn.interview.domain.dto.ErrorResponse;
import inflearn.interview.domain.dto.PageInfo;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public Page<PostDTO> postList(@RequestParam int page, @RequestBody PageInfo pageInfo) {
        return postService.getAllPost(pageInfo, page);
    }

    @GetMapping("/{postId}")
    public PostDTO postDetail(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/write")
    public ResponseEntity<String> postWrite(@RequestBody @Validated(PostDTO.valid1.class) PostDTO postDTO) {

        //PostDto를 서비스로 넘기기
        Long id = postService.createPost(postDTO);

        //저장
        return ResponseEntity.status(HttpStatus.CREATED).body(id.toString());
    }

    @PatchMapping("/{postId}/edit")
    public void postEdit(@PathVariable Long postId, @RequestBody @Validated(PostDTO.valid1.class) PostDTO postDTO) {
        postService.updatePost(postId, postDTO);
    }

    @DeleteMapping("/{postId}/delete")
    public void postDelete(@PathVariable Long postId, @RequestBody @Validated(PostDTO.valid1.class) PostDTO postDTO) {
        postService.deletePost(postId); // TODO 수정 필요
    }

    @PostMapping("/{postId}/like")
    public void postLike(@PathVariable Long postId, @RequestBody @Validated(PostDTO.valid2.class) PostDTO postDTO) {
        Long userId = postDTO.getUserId();
        postService.likePost(postId, userId);
    }

}
