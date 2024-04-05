package inflearn.interview.repository;

import inflearn.interview.domain.dao.VideoDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoDAO, Long> {
}