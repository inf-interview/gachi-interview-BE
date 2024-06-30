package inflearn.interview.postcomment.service;

import inflearn.interview.post.domain.Post;
import inflearn.interview.post.domain.PostCreate;
import inflearn.interview.post.service.PostService;
import inflearn.interview.postcomment.controller.response.PostCommentCreateResponse;
import inflearn.interview.postcomment.controller.response.PostCommentListResponse;
import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.postcomment.domain.PostCommentCreate;
import inflearn.interview.postcomment.domain.PostCommentDelete;
import inflearn.interview.postcomment.domain.PostCommentUpdate;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.service.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostCommentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private PostCommentRepository postCommentRepository;

    private Long userId;
    private Long postId;

    @BeforeEach
    void initUser() {
        UserCreate userCreate = UserCreate.builder()
                .name("상욱")
                .email("thstkddnr20@naver.com")
                .social("KAKAO")
                .createdAt(LocalDateTime.now())
                .image("imageLink")
                .role("USER")
                .build();

        User user = User.from(userCreate);
        user = userRepository.save(user);
        this.userId = user.getId();

        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);
        this.postId = post.getId();
    }

    @Test
    @DisplayName("사용자는 게시글에 댓글을 달 수 있다")
    void test1() {
        PostCommentCreate postCommentCreate = PostCommentCreate.builder()
                .userId(userId)
                .content("안녕하세요!")
                .build();

        PostCommentCreateResponse response = postCommentService.create(postCommentCreate, postId);
        PostComment postComment = postCommentRepository.findById(response.getCommentId()).orElseThrow();

        assertThat(response.getCommentId()).isEqualTo(postComment.getId());
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getContent()).isEqualTo("안녕하세요!");

    }

    @Test
    @DisplayName("사용자는 댓글을 수정할 수 있다")
    void test2() {
        PostCommentCreate postCommentCreate = PostCommentCreate.builder()
                .userId(userId)
                .content("안녕하세요!")
                .build();

        PostCommentCreateResponse response = postCommentService.create(postCommentCreate, postId);

        PostCommentUpdate postCommentUpdate = PostCommentUpdate.builder()
                .userId(userId)
                .commentId(response.getCommentId())
                .content("안녕안녕하세요!")
                .build();

        postCommentService.update(postCommentUpdate);

        PostComment updated = postCommentRepository.findById(response.getCommentId()).orElseThrow();

        assertThat(updated.getContent()).isEqualTo("안녕안녕하세요!");
        assertThat(updated.getUpdatedAt()).isNotNull();

    }

    @Test
    @DisplayName("사용자는 댓글을 삭제할 수 있다")
    void test3() {
        PostCommentCreate postCommentCreate = PostCommentCreate.builder()
                .userId(userId)
                .content("안녕하세요!")
                .build();

        PostCommentCreateResponse response = postCommentService.create(postCommentCreate, postId);

        PostCommentDelete postCommentDelete = PostCommentDelete.builder()
                .userId(userId)
                .commentId(response.getCommentId())
                .build();

        postCommentService.delete(postCommentDelete);

        Optional<PostComment> getComment = postCommentRepository.findById(response.getCommentId());

        assertThat(getComment).isEmpty();

    }

    @Test
    @DisplayName("사용자는 게시글에 달린 댓글 목록을 조회할 수 있다")
    void test4() {
        PostCommentCreate postCommentCreate1 = PostCommentCreate.builder()
                .userId(userId)
                .content("안녕하세요!")
                .build();

        postCommentService.create(postCommentCreate1, postId);

        PostCommentCreate postCommentCreate2 = PostCommentCreate.builder()
                .userId(userId)
                .content("안녕안녕!")
                .build();

        postCommentService.create(postCommentCreate2, postId);

        List<PostCommentListResponse> comments = postCommentService.getComments(postId);

        assertThat(comments).size().isEqualTo(2);

    }



}