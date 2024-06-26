package inflearn.interview.notice.service;

import inflearn.interview.notice.domain.Notice;
import inflearn.interview.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByUser(User user);
}
