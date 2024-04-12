package inflearn.interview.repository;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class PostSaveTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    void 게시글_저장() {
        User user = new User();
        user.setName("상욱");
        user.setEmail("AAA@naver.com");
        userRepository.save(user);

        Post post = new Post(user,"title", "content", "태그", "STUDY");
        postRepository.save(post);
    }
}
