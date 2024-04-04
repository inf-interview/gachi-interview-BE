package inflearn.interview.controller;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.dto.PostRequest;
import inflearn.interview.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 작성
    @PostMapping("/write")
    public ResponseEntity<String> writePost(@RequestBody PostRequest postRequest, BindingResult bindingResult) {
        //받아온 정보에 오류가 있는지 bindingResult로 확인
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("값 입력 X");
        }

        //PostRequest -> Post 객체 생성하여 값 넣어주기
        Post post = new Post(postRequest.getPostTitle(), postRequest.getContent(), postRequest.getTag(), postRequest.getCategory());

        //저장
        Long postId = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postId.toString());
    }
}
