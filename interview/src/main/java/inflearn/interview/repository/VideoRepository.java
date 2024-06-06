package inflearn.interview.repository;

import inflearn.interview.domain.Video;
import inflearn.interview.domain.dto.MyPostDTO;
import inflearn.interview.domain.dto.MyVideoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>, CustomVideoRepository {
    List<Video> findByUser_UserId(Long userId);
    @Query("select new inflearn.interview.domain.dto.MyVideoDTO(v, count(c.id)) " +
            "from Video v " +
            "left join VideoComment c on v.videoId = c.video.videoId " +
            "where v.user.userId=:userId group by v.videoId order by v.videoId desc")
    List<MyVideoDTO> findVideoByUserId(@Param("userId") Long userId);
}