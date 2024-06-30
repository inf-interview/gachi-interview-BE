package inflearn.interview.post.service;

import inflearn.interview.post.controller.response.PostDetailResponse;
import inflearn.interview.post.controller.response.PostResponse;
import inflearn.interview.post.domain.Post;
import inflearn.interview.post.domain.PostCreate;
import inflearn.interview.post.domain.PostDelete;
import inflearn.interview.post.domain.PostUpdate;
import inflearn.interview.postlike.controller.response.LikeResponse;
import inflearn.interview.postlike.domain.PostLikeRequest;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.service.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private Long userId;

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
    }

    @Test
    @DisplayName("사용자는 PostCreate로 게시글을 생성할 수 있다")
    void test1() {

        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);

        assertThat(post.getTitle()).isEqualTo("게시글 제목");
        assertThat(post.getContent()).isEqualTo("내용내용");
        assertThat(post.getCategory()).isEqualTo("studies");
        assertThat(post.getTag()).isEqualTo("안녕.하세요.");
        assertThat(post.getNumOfLike()).isEqualTo(0);

    }

    @Test
    @DisplayName("사용자는 PostUpdate로 게시글을 수정할 수 있다")
    void test2() {

        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);

        PostUpdate postUpdate = PostUpdate.builder()
                .userId(userId)
                .postTitle("게시글 제목제목")
                .content("내용내용내용")
                .category("studies")
                .tag(new String[]{"안녕안녕", "하세요!"})
                .build();

        postService.update(post.getId(), postUpdate);

    }

    @Test
    @DisplayName("사용자는 PostDelete로 게시글을 삭제할 수 있다")
    void test3() {

        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);

        PostDelete postDelete = PostDelete.builder()
                .userId(userId)
                .postId(post.getId())
                .build();

        postService.delete(postDelete);

        Optional<Post> findPost = postRepository.findById(post.getId());
        assertThat(findPost).isEmpty();

    }

    @Test
    @DisplayName("사용자는 게시글에 좋아요를 누를 수 있다")
    void test4() {
        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);

        PostLikeRequest postLikeRequest = PostLikeRequest.builder()
                .postId(post.getId())
                .userId(userId)
                .build();

        LikeResponse likeResponse = postService.likePost(postLikeRequest);

        Post likedPost = postRepository.findById(post.getId()).orElseThrow();

        assertThat(likeResponse.getNumOfLike()).isEqualTo(1);
        assertThat(likeResponse.isLiked()).isTrue();
        assertThat(likedPost.getNumOfLike()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자는 누른 좋아요를 취소할 수 있다")
    void test5() {
        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);

        PostLikeRequest postLikeRequest = PostLikeRequest.builder()
                .postId(post.getId())
                .userId(userId)
                .build();

        //좋아요 로직 2번 타도록
        postService.likePost(postLikeRequest);
        LikeResponse likeResponse = postService.likePost(postLikeRequest);

        assertThat(likeResponse.getNumOfLike()).isEqualTo(0);
        assertThat(likeResponse.isLiked()).isFalse();
    }

    @Test
    @DisplayName("사용자는 게시글 상세보기를 조회할 수 있다")
    void test6() {
        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);

        User writer = userRepository.findPostWriter(post.getId());

        PostDetailResponse response = postService.getPostDetail(post.getId(), userId);
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getUserName()).isEqualTo(writer.getName());
        assertThat(response.getPostId()).isEqualTo(post.getId());
        assertThat(response.getPostTitle()).isEqualTo("게시글 제목");
        assertThat(response.getCategory()).isEqualTo("studies");
//        assertThat(response.getTime()).isEqualTo(post.getCreatedAt());
        assertThat(response.getNumOfLike()).isEqualTo(0);
        assertThat(response.getNumOfComment()).isEqualTo(0);
        assertThat(response.isLiked()).isFalse();

    }

    @Test
    @DisplayName("사용자는 게시글 상세보기를 조회할 때 본인이 좋아요를 눌렀는지도 같이 보여준다")
    void test7() {
        PostCreate postCreate = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목")
                .content("내용내용")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        Post post = postService.create(postCreate);

        User writer = userRepository.findPostWriter(post.getId());

        PostLikeRequest postLikeRequest = PostLikeRequest.builder()
                .postId(post.getId())
                .userId(userId)
                .build();

        postService.likePost(postLikeRequest);

        PostDetailResponse response = postService.getPostDetail(post.getId(), userId);
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getUserName()).isEqualTo(writer.getName());
        assertThat(response.getPostId()).isEqualTo(post.getId());
        assertThat(response.getPostTitle()).isEqualTo("게시글 제목");
        assertThat(response.getCategory()).isEqualTo("studies");
//        assertThat(response.getTime()).isEqualTo(post.getCreatedAt());
        assertThat(response.getNumOfLike()).isEqualTo(1);
        assertThat(response.getNumOfComment()).isEqualTo(0);
        assertThat(response.isLiked()).isTrue();

    }

    @Test
    @DisplayName("사용자는 sortType, category, keyword를 이용하여 게시글 목록을 조회할 수 있다")
    void test8() {

        PostCreate postCreate1 = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목1")
                .content("내용내용1")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        postService.create(postCreate1);

        PostCreate postCreate2 = PostCreate.builder()
                .userId(userId)
                .postTitle("게시글 제목2")
                .content("내용내용2")
                .category("studies")
                .tag(new String[]{"안녕", "하세요"})
                .build();

        postService.create(postCreate2);


        Page<PostResponse> response1 = postService.getAllPost("new", "studies", "", 1);
        assertThat(response1).size().isEqualTo(2);

        Page<PostResponse> response2 = postService.getAllPost("new", "reviews", "", 1);
        assertThat(response2).isEmpty();
    }



}