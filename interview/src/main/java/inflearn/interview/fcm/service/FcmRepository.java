package inflearn.interview.fcm.service;

import inflearn.interview.fcm.domain.Fcm;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmRepository {
    Optional<Fcm> findByUserId(Long userId);

    Optional<Fcm> findByUser(User user);

    void save(Fcm fcm);

}