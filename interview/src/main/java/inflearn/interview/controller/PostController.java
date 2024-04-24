package inflearn.interview.controller;

import inflearn.interview.domain.dto.PageInfo;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> postWrite(@RequestBody @Valid PostDTO postDTO, BindingResult bindingResult) {
        //받아온 정보에 오류가 있는지 bindingResult로 확인
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("값 입력 X");
        }

        //PostDto를 서비스로 넘기기
        Long id = postService.createPost(postDTO);

        //저장
        return ResponseEntity.status(HttpStatus.CREATED).body(id.toString());
    }

    @PatchMapping("/{postId}/edit")
    public void postEdit(@PathVariable Long postId, @RequestBody PostDTO postDTO) {
        postService.updatePost(postId, postDTO);
    }

    @DeleteMapping("/{postId}/delete")
    public void postDelete(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @PostMapping("/{postId}/like")
    public void postLike(@PathVariable Long postId, @RequestBody PostDTO postDTO) {
        Long userId = postDTO.getUserId();
        postService.likePost(postId, userId);
    }


}
