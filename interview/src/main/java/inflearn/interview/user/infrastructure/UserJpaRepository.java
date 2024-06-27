package inflearn.interview.user.infrastructure;

import inflearn.interview.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u where u.email=:email and u.social=:social")
    Optional<UserEntity> findUserByEmailAndSocial(@Param("email") String email, @Param("social") String social);

    @Query("select p.userEntity from PostEntity p where p.id=:id")
    User findWriter(@Param("id") Long postId);

}
