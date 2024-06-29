package inflearn.interview.post.controller;

import inflearn.interview.post.controller.response.PostCreateResponse;
import inflearn.interview.post.controller.response.PostDetailResponse;
import inflearn.interview.post.controller.response.PostResponse;
import inflearn.interview.post.controller.response.PostUpdateResponse;
import inflearn.interview.post.domain.Post;
import inflearn.interview.post.domain.PostCreate;
import inflearn.interview.post.domain.PostDelete;
import inflearn.interview.post.domain.PostUpdate;
import inflearn.interview.postlike.controller.response.LikeResponse;
import inflearn.interview.postlike.domain.PostLikeRequest;
import inflearn.interview.user.domain.User;
import inflearn.interview.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public Page<PostResponse> postList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "new") String sortType,
                                       @RequestParam String category,
                                       @RequestParam(defaultValue = "") String keyword) {
        return postService.getAllPost(sortType, category, keyword, page);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> postDetail(@PathVariable Long postId, @AuthenticationPrincipal User user) {
        PostDetailResponse response = postService.getPostDetail(postId, user.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/write")
    public ResponseEntity<PostCreateResponse> postWrite(@RequestBody @Validated(PostResponse.valid1.class) PostCreate postCreate) {

        //PostDto를 서비스로 넘기기
        Post post = postService.create(postCreate);

        PostCreateResponse response = PostCreateResponse.from(post);

        //저장
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{postId}/edit")
    public ResponseEntity<?> postEdit(@PathVariable Long postId, @RequestBody PostUpdate postUpdate) {
        Post post = postService.update(postId, postUpdate);

        PostUpdateResponse response = PostUpdateResponse.from(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<?> postDelete(@PathVariable Long postId, @RequestBody PostDelete postDelete) {
        postService.deletePost(postDelete);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> postLike(@PathVariable Long postId, @RequestBody PostLikeRequest postLike) {
        LikeResponse likeResponse = postService.likePost(postLike);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponse);
    }

}
