package inflearn.interview.repository;

import inflearn.interview.domain.dao.VideoCommentDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentRepository extends JpaRepository<VideoCommentDAO, Long> {
}