package inflearn.interview.repository;

import inflearn.interview.domain.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoCommentRepository extends JpaRepository<VideoComment, Long> {

    @Query("select vc from VideoComment vc where vc.video.videoId=:videoId")
    List<VideoComment> findCommentList(@Param("videoId") Long videoId);
}