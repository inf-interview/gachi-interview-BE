package inflearn.interview.notice.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByUserEntity(UserEntity userEntity);
}
