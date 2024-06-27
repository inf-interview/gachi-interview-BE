package inflearn.interview.video.infrastructure;

import inflearn.interview.video.controller.response.MyVideoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoJpaRepository extends JpaRepository<VideoEntity, Long> {

    @Query("select new inflearn.interview.video.controller.response.MyVideoResponse(v, count(c.id)) " +
            "from VideoEntity v " +
            "left join VideoCommentEntity c on v.id = c.videoEntity.id " +
            "where v.userEntity.id=:userId group by v.id order by v.id desc")
    List<MyVideoResponse> findVideoByUserId(@Param("userId") Long userId);
}
