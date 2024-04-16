package inflearn.interview.repository;

import inflearn.interview.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("select pc from PostComment pc where pc.post.postId=:postId")
    List<PostComment> findCommentList(@Param("postId") Long postId);

    @Query("select pc from PostComment pc where pc.user.userId=:userId")
    List<PostComment> findCommentByUserId(@Param("userId") Long userId);
}
