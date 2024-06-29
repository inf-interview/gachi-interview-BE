package inflearn.interview.post.infrastructure;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inflearn.interview.post.controller.response.PostResponse;
import inflearn.interview.post.controller.response.QPostResponse;
import inflearn.interview.post.service.CustomPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static inflearn.interview.post.infrastructure.QPostEntity.postEntity;
import static inflearn.interview.postcomment.infrastructure.QPostCommentEntity.postCommentEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<PostResponse> findAllPostByPageInfo(String sortType, String category, String keyword, Pageable pageable) {

        List<PostResponse> result = jpaQueryFactory
                .select(new QPostResponse(
                        postEntity.userEntity.id,
                        postEntity.userEntity.name,
                        postEntity.id,
                        postEntity.title,
                        postEntity.content,
                        postEntity.category,
                        postEntity.createdAt,
                        postEntity.updatedAt,
                        postEntity.numOfLike,
                        select(postCommentEntity.count()).from(postCommentEntity).where(postCommentEntity.postEntity.id.eq(postEntity.id)),
                        postEntity.tag,
                        postEntity.userEntity.image))
                .from(postEntity)
                .where(categoryEq(category))
                .where(keywordEq(keyword))
                .orderBy(sortTypeEq(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(categoryEq(category))
                .where(keywordEq(keyword))
                .fetchOne();

        total = (total != null) ? total : 0L;

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression categoryEq(String category) { //all, study, interview

        switch (category) {
            case "studies" -> {
                return postEntity.category.eq("studies");
            }
            case "reviews" -> {
                return postEntity.category.eq("reviews");
            }
            default -> {
                return null;
            }
        }
    }

    private BooleanExpression keywordEq(String keyword) {
        if (!keyword.isEmpty()) {
            return postEntity.title.toLowerCase().contains(keyword).or(postEntity.tag.toLowerCase().contains(keyword));
        }
        return null;
    }

    private OrderSpecifier<?> sortTypeEq(String sortType) { //동적 sortType

        OrderSpecifier<?> orderSpecifier =
                switch (sortType) {
            case "like" -> postEntity.numOfLike.desc();
            case "new" -> postEntity.createdAt.desc();
            default -> postEntity.createdAt.desc();
        };

        return orderSpecifier;

    }

}
