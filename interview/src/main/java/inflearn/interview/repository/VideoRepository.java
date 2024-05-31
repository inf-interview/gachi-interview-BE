package inflearn.interview.repository;

import inflearn.interview.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>, CustomVideoRepository {
    List<Video> findByUser_UserId(Long userId);

}