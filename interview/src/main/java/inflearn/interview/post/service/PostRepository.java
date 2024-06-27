package inflearn.interview.post.service;

import inflearn.interview.post.controller.response.MyPostResponse;
import inflearn.interview.post.domain.Post;
import inflearn.interview.post.controller.response.PostResponse;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<PostResponse> findPostByPostId(Long postId);

    Optional<Post> findById(Long postId);

    Post save(Post post);

    void delete(Post post);

    List<MyPostResponse> findMyPost(Long userId, String category);
}
