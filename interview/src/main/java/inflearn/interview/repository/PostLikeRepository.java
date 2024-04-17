package inflearn.interview.repository;

import inflearn.interview.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select pl from PostLike pl where pl.user.userId=:userId and pl.post.postId=:postId")
    Optional<PostLike> findPostLikeByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
