package inflearn.interview.repository;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.dto.MyPostDTO;
import inflearn.interview.domain.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new inflearn.interview.domain.dto.MyPostDTO(p, count(c.postCommentId)) " +
            "from Post p " +
            "left join PostComment c on p.postId = c.post.postId " +
            "where p.user.userId=:userId group by p.postId order by p.postId desc")
    List<MyPostDTO> findPostByUserId(@Param("userId") Long userId);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
