package inflearn.interview.postcomment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostCommentJpaRepository extends JpaRepository<PostCommentEntity, Long> {

    @Query("select pc from PostCommentEntity pc where pc.postEntity.id=:postId")
    List<PostCommentEntity> findCommentList(@Param("postId") Long postId);

    @Query("select pc from PostCommentEntity pc where pc.userEntity.id=:userId")
    List<PostCommentEntity> findCommentByUserId(@Param("userId") Long userId);

}
