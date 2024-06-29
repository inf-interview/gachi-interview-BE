package inflearn.interview.fcm.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmJpaRepository extends JpaRepository<FcmEntity, Long> {

    Optional<FcmEntity> findByUserEntityId(Long userId);
}
