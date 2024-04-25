package inflearn.interview.repository;

import inflearn.interview.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select User from User u where u.email=:email and u.social=:kakao")
    Optional<User> findKakaoUserByEmail(@Param("email") String email, @Param("kakao") String kakao);

    @Query("select User from User u where u.email=:email and u.social=:google")
    Optional<User> findGoogleUserByEmail(@Param("email") String email, @Param("google") String google);

}
