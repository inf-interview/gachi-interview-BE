package inflearn.interview.service;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    @Transactional
    void before() {
        User user = new User();
        user.setName("상욱");
        user.setEmail("AAA@naver.com");
        userRepository.save(user);
    }

    @Test
    @Transactional
    void createPost() {
        User user = userRepository.findById(1L).get();
        Post post = new Post(user, "title", "content", null, "스터디");
        PostDTO postDTO = new PostDTO(post);

        Long id = postService.createPost(postDTO);
        PostDTO getPost = postService.getPostById(id);

        assertThat(postDTO.getPostTitle()).isEqualTo(getPost.getPostTitle());
    }

    @Test
    @Transactional
    void updatePost() {
        User user = userRepository.findById(1L).get();
        Post post = new Post(user, "title", "content", null, "스터디");
        PostDTO postDTO = new PostDTO(post);

        Long id = postService.createPost(postDTO);

        Post newPost = new Post(user, "new title", "new content", null, "스터디");
        PostDTO postDTO2 = new PostDTO(newPost);


        postService.updatePost(id, postDTO2);

        PostDTO getPost = postService.getPostById(1L);

        assertThat(getPost.getPostTitle()).isEqualTo("new title");
    }

    @Test
    @Rollback(value = false)
    void deletePost() {
        User user = userRepository.findById(1L).get();
        Post post = new Post(user, "title", "content", null, "스터디");
        PostDTO postDTO = new PostDTO(post);

        Long id = postService.createPost(postDTO);

        postService.deletePost(id);
    }
}
