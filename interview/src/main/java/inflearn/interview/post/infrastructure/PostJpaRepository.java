package inflearn.interview.post.infrastructure;

import inflearn.interview.post.domain.Post;
import inflearn.interview.post.domain.MyPostDTO;
import inflearn.interview.post.controller.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    @Query("select new inflearn.interview.post.domain.MyPostDTO(p, count(c.postCommentId)) " +
            "from PostEntity p " +
            "left join PostCommentEntity c on p.id = c.post.id " +
            "where p.user.userId=:userId and p.category=:category group by p.postId order by p.postId desc")
    List<MyPostDTO> findPostByUserId(@Param("userId") Long userId, @Param("category") String category);

    @Query("select new inflearn.interview.post.domain.PostResponse(p, count(c.postCommentId)) " +
            "from Post p " +
            "left join PostCommentEntity c on p.postId = c.post.postId " +
            "where p.postId=:postId group by p.postId")
    Optional<PostResponse> findPostByPostId(@Param("postId") Long postId);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
