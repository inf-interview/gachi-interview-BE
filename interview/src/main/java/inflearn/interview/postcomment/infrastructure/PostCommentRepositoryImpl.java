package inflearn.interview.postcomment.infrastructure;

import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.postcomment.service.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepository {

    private final PostCommentJpaRepository postCommentJpaRepository;

    @Override
    public PostComment save(PostComment postComment) {
        return postCommentJpaRepository.save(PostCommentEntity.fromModel(postComment)).toModel();
    }

    @Override
    public Optional<PostComment> findById(Long id) {
        return postCommentJpaRepository.findById(id).map(PostCommentEntity::toModel);
    }

    @Override
    public void delete(PostComment postComment) {
        postCommentJpaRepository.delete(PostCommentEntity.fromModel(postComment));
    }

    @Override
    public List<PostCommentEntity> findCommentList(Long postId) {
        return postCommentJpaRepository.findCommentList(postId);
    }

    @Override
    public Integer getCommentCount(Long postId) {
        Integer commentCount = postCommentJpaRepository.findCommentCount(postId);
        if (commentCount == null) {
            return 0;
        }
        return commentCount;
    }

    @Override
    public List<PostCommentEntity> findMyComment(Long userId) {
        return postCommentJpaRepository.findMyComment(userId);
    }
}
