package inflearn.interview.service;

import inflearn.interview.domain.Post;
import inflearn.interview.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시글 생성
    public Long createPost(Post post) {
        postRepository.save(post);
        return post.getPostId();
    }
}
