package inflearn.interview.postlike.service;

import inflearn.interview.postlike.domain.PostLike;
import inflearn.interview.postlike.infrastructure.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository {

    Optional<PostLike> findPostLike(Long userId, Long postId);

    PostLike save(PostLike postLike);

    void delete(PostLike postLike);
}
