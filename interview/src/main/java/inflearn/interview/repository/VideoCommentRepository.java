package inflearn.interview.repository;

import inflearn.interview.domain.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentRepository extends JpaRepository<VideoComment, Long> {
}