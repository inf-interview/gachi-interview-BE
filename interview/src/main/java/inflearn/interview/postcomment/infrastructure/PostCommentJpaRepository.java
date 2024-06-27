package inflearn.interview.postcomment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostCommentJpaRepository extends JpaRepository<PostCommentEntity, Long> {

    @Query("select pc from PostCommentEntity pc where pc.postEntity.id=:postId")
    List<PostCommentEntity> findCommentList(@Param("postId") Long postId);

    @Query("select pc from PostCommentEntity pc where pc.userEntity.id=:userId")
    List<PostCommentEntity> findMyComment(@Param("userId") Long userId);

    @Query("select count(p.postCommentEntities) from PostEntity p where p.id=:postId")
    int findCommentCount(@Param("postId") Long postId);
}
