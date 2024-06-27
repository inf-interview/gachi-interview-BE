package inflearn.interview.postcomment.service;

import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.postcomment.infrastructure.PostCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostCommentRepository {

    PostComment save(PostComment postComment);

    Optional<PostComment> findById(Long id);

    void delete(PostComment postComment);

    List<PostCommentEntity> findCommentList(Long postId);

    int getCommentCount(Long postId);

    List<PostCommentEntity> findMyComment(Long userId);
}
