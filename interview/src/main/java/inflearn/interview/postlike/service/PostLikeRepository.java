package inflearn.interview.postlike.service;

import inflearn.interview.post.domain.Post;
import inflearn.interview.postlike.domain.PostLike;

import java.util.Optional;

public interface PostLikeRepository {

    Optional<PostLike> findPostLike(Long userId, Long postId);

    PostLike save(PostLike postLike);

    void delete(PostLike postLike);

    void deleteByPost(Post post);
}
