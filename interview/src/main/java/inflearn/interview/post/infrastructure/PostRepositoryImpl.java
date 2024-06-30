package inflearn.interview.post.infrastructure;

import inflearn.interview.post.controller.response.PostResponse;
import inflearn.interview.post.controller.response.MyPostResponse;
import inflearn.interview.post.domain.Post;
import inflearn.interview.post.service.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Optional<PostResponse> findPostByPostId(Long postId) {
        return Optional.empty();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postJpaRepository.findById(postId).map(PostEntity::toModel);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.fromModel(post)).toModel();
    }

    @Override
    public void delete(Post post) {
        postJpaRepository.delete(PostEntity.fromModel(post));
    }

    @Override
    public List<MyPostResponse> findMyPost(Long userId, String category) {
        return postJpaRepository.findPostByUserId(userId, category);
    }
}
