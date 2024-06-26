package inflearn.interview.postlike.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeJpaRepository extends JpaRepository<PostLikeEntity, Long> {

    @Query("select pl from PostLikeEntity pl where pl.userEntity.id=:userId and pl.postEntity.id=:postId")
    Optional<PostLikeEntity> findPostLikeByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
