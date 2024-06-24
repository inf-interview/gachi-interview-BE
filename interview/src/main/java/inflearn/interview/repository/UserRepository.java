package inflearn.interview.repository;

import inflearn.interview.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndSocial(String email, String social);

    @Query("select u from User u where u.role=:admin")
    User findAdmin(@Param("admin") String admin);
}
