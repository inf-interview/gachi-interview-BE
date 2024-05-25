package inflearn.interview.repository;

import inflearn.interview.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndSocial(String email, String social);
}
