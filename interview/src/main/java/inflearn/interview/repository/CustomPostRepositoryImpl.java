package inflearn.interview.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.domain.dto.QPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static inflearn.interview.domain.QPost.post;
import static inflearn.interview.domain.QPostComment.postComment;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<PostDTO> findAllPostByPageInfo(String sortType, String category, Pageable pageable) {

        List<PostDTO> result = jpaQueryFactory
                .select(new QPostDTO(
                        post.user.userId,
                        post.user.name,
                        post.postId,
                        post.title,
                        post.content,
                        post.category,
                        post.createdAt,
                        post.updatedAt,
                        post.numOfLike,
                        select(postComment.count()).from(postComment).where(postComment.post.postId.eq(post.postId)),
                        post.tag,
                        post.user.image))
                .from(post)
                .where(categoryEq(category))
                .orderBy(sortTypeEq(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(categoryEq(category))
                .fetch()
                .size();


        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression categoryEq(String category) { //all, study, interview

        switch (category) {
            case "studies" -> {
                return post.category.eq("studies");
            }
            case "reviews" -> {
                return post.category.eq("reviews");
            }
            default -> {
                return null;
            }
        }
    }

    private OrderSpecifier<?> sortTypeEq(String sortType) { //동적 sortType

        OrderSpecifier<?> orderSpecifier =
                switch (sortType) {
            case "like" -> post.numOfLike.desc();
            case "new" -> post.createdAt.desc();
            default -> post.createdAt.desc();
        };

        return orderSpecifier;

    }

}
