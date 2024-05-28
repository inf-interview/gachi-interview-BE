package inflearn.interview.controller;

import inflearn.interview.aop.ValidateUser;
import inflearn.interview.domain.dto.ErrorResponse;
import inflearn.interview.domain.dto.LikeNumDTO;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.exception.RequestDeniedException;
import inflearn.interview.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public Page<PostDTO> postList(@RequestParam int page, @RequestParam String sortType, @RequestParam String category) {
        return postService.getAllPost(sortType, category, page);
    }

    @GetMapping("/{postId}")
    public PostDTO postDetail(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @ValidateUser
    @PostMapping("/write")
    public ResponseEntity<PostDTO> postWrite(@RequestBody @Validated(PostDTO.valid1.class) PostDTO postDTO) {

        //PostDto를 서비스로 넘기기
        PostDTO post = postService.createPost(postDTO);

        //저장
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @ValidateUser
    @PatchMapping("/{postId}/edit")
    public ResponseEntity<?> postEdit(@PathVariable Long postId, @RequestBody @Validated(PostDTO.valid1.class) PostDTO postDTO) {
        try {
            PostDTO getDTO = postService.updatePost(postId, postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(getDTO);
        } catch (RequestDeniedException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Access Denied", "권한이 없습니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

    }

    @ValidateUser
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<?> postDelete(@PathVariable Long postId, @RequestBody @Validated(PostDTO.valid2.class) PostDTO postDTO) {
        Long userId = postDTO.getUserId();
        try {
            postService.deletePost(postId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RequestDeniedException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Access Denied", "권한이 없습니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @ValidateUser
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> postLike(@PathVariable Long postId, @RequestBody @Validated(PostDTO.valid2.class) PostDTO postDTO) {
        Long userId = postDTO.getUserId();
        LikeNumDTO numOfLike = postService.likePost(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(numOfLike);
    }

}
