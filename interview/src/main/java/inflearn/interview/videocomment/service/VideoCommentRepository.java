package inflearn.interview.videocomment.service;

import inflearn.interview.videocomment.domain.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoCommentRepository extends JpaRepository<VideoComment, Long> {

    @Query("select vc from VideoComment vc where vc.video.videoId=:videoId")
    List<VideoComment> findCommentList(@Param("videoId") Long videoId);

    @Query("select vc from VideoComment vc where vc.user.userId=:userId")
    List<VideoComment> findCommentByUserId(@Param("userId") Long userId);
}