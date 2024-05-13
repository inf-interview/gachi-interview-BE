package inflearn.interview.service;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.PostComment;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.repository.PostCommentRepository;
import inflearn.interview.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostCommentServiceTest {

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @BeforeEach
    @Transactional
    void before() {
        User user = new User();
        user.setName("상욱");
        user.setEmail("AAA@naver.com");
        userRepository.save(user);

        Post post = new Post(user,"A", "content", null, "스터디");
        PostDTO postDTO = new PostDTO(post);
        PostDTO post1 = postService.createPost(postDTO);


        PostCommentDTO postCommentDTO = new PostCommentDTO();
        postCommentDTO.setUsername("상욱");
        postCommentDTO.setUserId(1L);
        postCommentDTO.setContent("AA");
        postCommentDTO.setCreatedAt(LocalDateTime.now());

        PostCommentDTO comment = postCommentService.createComment(postCommentDTO, postDTO.getPostId());

        PostCommentDTO postCommentDTO2 = new PostCommentDTO();
        postCommentDTO2.setUsername("상욱");
        postCommentDTO2.setUserId(1L);
        postCommentDTO2.setContent("ABC");
        postCommentDTO2.setCreatedAt(LocalDateTime.now());

        PostCommentDTO comment2 = postCommentService.createComment(postCommentDTO2, postDTO.getPostId());

    }

    @Test
    void getComment() {
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updateComment() {
        //새로 갱신할 DTO
        PostCommentDTO postCommentDTO = new PostCommentDTO();
        postCommentDTO.setUsername("상욱");
        postCommentDTO.setUserId(1L);
        postCommentDTO.setContent("AA - 수정완료");
        postCommentDTO.setCreatedAt(LocalDateTime.now());


    }

    @Test
    @Transactional
    void deleteComment() {
    }

    @Test
    void getAllComments() {
        List<PostCommentDTO> comments = postCommentService.getComments(1L);
        assertEquals(2, comments.size());
    }



}