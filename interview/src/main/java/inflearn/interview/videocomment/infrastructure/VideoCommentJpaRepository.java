package inflearn.interview.videocomment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoCommentJpaRepository extends JpaRepository<VideoCommentEntity, Long> {

    @Query("select vc from VideoCommentEntity vc where vc.videoEntity.id=:videoId")
    List<VideoCommentEntity> findCommentList(@Param("videoId") Long videoId);

    @Query("select vc from VideoCommentEntity vc where vc.userEntity.id=:userId")
    List<VideoCommentEntity> findCommentByUserId(@Param("userId") Long userId);
}
