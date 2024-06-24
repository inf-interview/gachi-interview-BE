package inflearn.interview.repository;

import inflearn.interview.domain.Notice;
import inflearn.interview.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByUser(User user);
}
