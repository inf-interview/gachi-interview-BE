package inflearn.interview.repository;

import inflearn.interview.domain.dao.VideoDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoDAO, Long> {
}