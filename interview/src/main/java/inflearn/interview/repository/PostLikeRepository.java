package inflearn.interview.repository;

import inflearn.interview.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select pl from PostLike pl where pl.user.email=:email and pl.post.postId=:postId")
    PostLike findPostLikeByEmailAndPostId(@Param("email") String email, @Param("postId") Long postId);
}
