package inflearn.interview.fcm.service;

import inflearn.interview.fcm.domain.Fcm;
import inflearn.interview.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FCMRepository extends JpaRepository<Fcm, Long> {
    Optional<Fcm> findByUserUserId(Long userId);

    Optional<Fcm> findByUser(User user);
}