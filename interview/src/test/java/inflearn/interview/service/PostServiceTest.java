package inflearn.interview.service;

import inflearn.interview.DataSweepExtension;
import inflearn.interview.TestRepository;
import inflearn.interview.domain.Post;
import inflearn.interview.domain.PostLike;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.PostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(DataSweepExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private TestRepository<User, Long> userRepository;

    @Autowired
    private TestRepository<Post, Long> postRepository;

    @Autowired
    private TestRepository<PostLike, Long> postLikeRepository;

    private Long userId;

    @BeforeEach
    void setUser() {
        User user = new User();
        user.setEmail("thstkddnr20@naver.com");
        user.setName("손상욱");
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("USER");
        user.setSocial("KAKAO");
        userRepository.save(user);
        this.userId = user.getUserId();
    }

    @Test
    @DisplayName("사용자는 스터디모집 게시글을 생성할 수 있다")
    void test1() {
        // given
        PostDTO postDTO = new PostDTO();
        postDTO.setUserId(userId);
        postDTO.setPostTitle("게시글");
        postDTO.setContent("내용");
        postDTO.setCategory("studies");
        postDTO.setTag(new String[]{"1", "2"});

        // when
        PostDTO dto = postService.createPost(postDTO);

        // then
        Post post = postRepository.findById(Post.class, dto.getPostId());
        assertThat(post.getTitle()).isEqualTo("게시글");
        assertThat(post.getCategory()).isEqualTo("studies");

    }

    @Test
    @DisplayName("사용자는 면접후기 게시글을 생성할 수 있다")
    void test2() {
        // given
        PostDTO postDTO = new PostDTO();
        postDTO.setUserId(userId);
        postDTO.setPostTitle("게시글");
        postDTO.setContent("내용");
        postDTO.setCategory("reviews");
        postDTO.setTag(new String[]{"1", "2"});

        // when
        PostDTO dto = postService.createPost(postDTO);

        // then
        Post post = postRepository.findById(Post.class, dto.getPostId());
        assertThat(post.getTitle()).isEqualTo("게시글");
        assertThat(post.getCategory()).isEqualTo("reviews");
    }

    @Test
    @DisplayName("사용자는 게시글을 수정할 수 있다")
    void test3() {
        // given
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setUserId(userId);
        postDTO1.setPostTitle("게시글");
        postDTO1.setContent("내용");
        postDTO1.setCategory("reviews");
        postDTO1.setTag(new String[]{"1", "2"});

        PostDTO dto = postService.createPost(postDTO1);
        Post post = postRepository.findById(Post.class, dto.getPostId());

        // when
        PostDTO postDTO2 = new PostDTO();
        postDTO2.setUserId(userId);
        postDTO2.setPostTitle("게시글 수정됐어요");
        postDTO2.setContent("내용");
        postDTO2.setCategory("reviews");
        postDTO2.setTag(new String[]{"1", "2"});
        postService.updatePost(post.getPostId(), postDTO2);

        // then
        Post updatedPost = postRepository.findById(Post.class, dto.getPostId());
        assertThat(updatedPost.getTitle()).isEqualTo("게시글 수정됐어요");

    }

    @Test
    @DisplayName("사용자는 게시글을 삭제시킬 수 있다")
    void test4() { // userId를 굳이 넣어야 할까?
        // given
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setUserId(userId);
        postDTO1.setPostTitle("게시글");
        postDTO1.setContent("내용");
        postDTO1.setCategory("reviews");
        postDTO1.setTag(new String[]{"1", "2"});

        PostDTO dto = postService.createPost(postDTO1);
        Post post = postRepository.findById(Post.class, dto.getPostId());

        // when
        postService.deletePost(post.getPostId(), userId);

        // then
        assertThat(postRepository.findById(Post.class, dto.getPostId())).isNull();
    }

    @Test
    @Transactional
    @DisplayName("사용자는 게시글에 좋아요를 누를 수 있다")
    void test5() {
        // given
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setUserId(userId);
        postDTO1.setPostTitle("게시글");
        postDTO1.setContent("내용");
        postDTO1.setCategory("reviews");
        postDTO1.setTag(new String[]{"1", "2"});

        PostDTO dto = postService.createPost(postDTO1);
        Post post = postRepository.findById(Post.class, dto.getPostId());

        // when
        postService.likePost(post.getPostId(), userId);

        // then
        assertThat(postLikeRepository.findById(PostLike.class, 1L).getPost()).isEqualTo(post); // getPost는 지연로딩이라 Transactional 없이 LazyInitializationException 발생
        assertThat(postLikeRepository.findById(PostLike.class, 1L).getUser()).isEqualTo(userRepository.findById(User.class, userId));
    }

    @Test
    @Transactional
    @DisplayName("사용자는 게시글에 좋아요를 누르고 한번 더 누르면 좋아요 취소가 된다")
    void test5_1() {
        // given
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setUserId(userId);
        postDTO1.setPostTitle("게시글");
        postDTO1.setContent("내용");
        postDTO1.setCategory("reviews");
        postDTO1.setTag(new String[]{"1", "2"});

        PostDTO dto = postService.createPost(postDTO1);
        Post post = postRepository.findById(Post.class, dto.getPostId());

        // when
        postService.likePost(post.getPostId(), userId);
        postService.likePost(post.getPostId(), userId);

        // then
        assertThat(postLikeRepository.findById(PostLike.class, 1L)).isNull();
    }

    /**
     * 게시글 조회
     */
    @Test
    @DisplayName("사용자는 하나의 게시글을 상세보기 할 수 있다 + 게시글 상세보기를 할 때 좋아요를 눌렀는지 안눌렀는지도 같이 보여준다")
    void test6() {
        // given
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setUserId(userId);
        postDTO1.setPostTitle("게시글");
        postDTO1.setContent("내용");
        postDTO1.setCategory("reviews");
        postDTO1.setTag(new String[]{"1", "2"});

        PostDTO dto = postService.createPost(postDTO1);
        Post post = postRepository.findById(Post.class, dto.getPostId());

        // when
        postService.likePost(post.getPostId(), userId); // 좋아요 누름
        PostDTO postDTO = postService.getPostById(post.getPostId(), userId);

        // then
        assertThat(postDTO.isLiked()).isTrue();
        assertThat(postDTO.getPostTitle()).isEqualTo("게시글");
        assertThat(postDTO.getContent()).isEqualTo("내용");
        assertThat(postDTO.getTag()).isEqualTo(new String[]{"1", "2"});
    }

    @Test
    @DisplayName("사용자는 게시글 목록을 sortType과 category를 이용하여 정렬하고, keyword를 통한 검색이 가능하다")
    void test7() {
        // given
        PostDTO postDTO1 = new PostDTO();
        postDTO1.setUserId(userId);
        postDTO1.setPostTitle("게시글");
        postDTO1.setContent("내용");
        postDTO1.setCategory("reviews");
        postDTO1.setTag(new String[]{"1", "2"});
        postService.createPost(postDTO1);

        PostDTO postDTO2 = new PostDTO();
        postDTO2.setUserId(userId);
        postDTO2.setPostTitle("게시글게시글");
        postDTO2.setContent("내용");
        postDTO2.setCategory("reviews");
        postDTO2.setTag(new String[]{"1", "2"});
        postService.createPost(postDTO2);

        // when
        Page<PostDTO> allPost = postService.getAllPost("new", "reviews", "게시글", 1);

        // then
        assertThat(allPost).isNotEmpty();
        assertThat(allPost).size().isNotNull();
        assertThat(allPost.getContent().get(0).getPostTitle()).isEqualTo("게시글게시글");
        assertThat(allPost.getContent().get(1).getPostTitle()).isEqualTo("게시글");

    }
}