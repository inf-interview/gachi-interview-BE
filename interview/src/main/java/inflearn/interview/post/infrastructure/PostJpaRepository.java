package inflearn.interview.post.infrastructure;

import inflearn.interview.post.controller.response.MyPostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    @Query("select new inflearn.interview.post.controller.response.MyPostResponse(p, count(c.id)) " +
            "from PostEntity p " +
            "left join PostCommentEntity c on p.id = c.postEntity.id " +
            "where p.userEntity.id=:userId and p.category=:category group by p.id order by p.id desc")
    List<MyPostResponse> findPostByUserId(@Param("userId") Long userId, @Param("category") String category);

}
