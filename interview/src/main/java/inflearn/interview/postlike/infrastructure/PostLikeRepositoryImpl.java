package inflearn.interview.postlike.infrastructure;

import inflearn.interview.postlike.domain.PostLike;
import inflearn.interview.postlike.service.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepository {

    private final PostLikeJpaRepository postLikeJpaRepository;

    public Optional<PostLike> findPostLike(Long userId, Long postId) {
        return postLikeJpaRepository.findPostLikeByUserIdAndPostId(userId, postId).map(PostLikeEntity::toModel);
    }

    @Override
    public PostLike save(PostLike postLike) {
        return postLikeJpaRepository.save(PostLikeEntity.fromModel(postLike)).toModel();
    }

    @Override
    public void delete(PostLike postLike) {
        postLikeJpaRepository.delete(PostLikeEntity.fromModel(postLike));
    }
}
