package inflearn.interview.repository;

import inflearn.interview.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class PostSaveTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @Rollback(value = false)
    void 게시글_저장() {
        Post post = new Post("title", "content", "태그", "STUDY");
        postRepository.save(post);
    }
}
