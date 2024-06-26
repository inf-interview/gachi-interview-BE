package inflearn.interview.fcm.infrastructure;

import inflearn.interview.fcm.domain.Fcm;
import inflearn.interview.fcm.service.FcmRepository;
import inflearn.interview.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FcmRepositoryImpl implements FcmRepository {

    private final FcmJpaRepository fcmJpaRepository;

    @Override
    public Optional<Fcm> findByUserId(Long userId) {
        fcmJpaRepository.findByUserEntityId(userId);
    }

    @Override
    public Optional<Fcm> findByUser(User user) {
        return Optional.empty();
    }

    @Override
    public void save(Fcm fcm) {
        fcmJpaRepository.save(fcm);
    }

}
