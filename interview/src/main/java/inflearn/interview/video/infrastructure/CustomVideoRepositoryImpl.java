package inflearn.interview.video.infrastructure;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inflearn.interview.video.domain.QVideoDTO2;
import inflearn.interview.video.domain.VideoDTO2;
import inflearn.interview.video.service.CustomVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static inflearn.interview.video.infrastructure.QVideoEntity.videoEntity;

@Repository
@RequiredArgsConstructor
public class CustomVideoRepositoryImpl implements CustomVideoRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<VideoDTO2> findAllVideoByPageInfo(String sortType, String keyword, Pageable pageable) {

        List<VideoDTO2> result = jpaQueryFactory
                .select(new QVideoDTO2(
                        videoEntity.userEntity.id,
                        videoEntity.userEntity.name,
                        videoEntity.id,
                        videoEntity.videoLink,
                        videoEntity.videoTitle,
                        videoEntity.time,
                        videoEntity.updatedTime,
                        videoEntity.numOfLike,
                        videoEntity.tag,
                        videoEntity.thumbnailLink,
                        videoEntity.userEntity.image
                ))
                .from(videoEntity)
                .where(videoEntity.exposure)
                .where(keywordEq(keyword))
                .orderBy(sortTypeEq(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = jpaQueryFactory
                .select(videoEntity.count())
                .from(videoEntity)
                .where(videoEntity.exposure)
                .where(keywordEq(keyword))
                .fetchOne();

        total = (total != null) ? total : 0L;

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression keywordEq(String keyword) {
        if (!keyword.isEmpty()) {
            return videoEntity.videoTitle.toLowerCase().contains(keyword).or(videoEntity.tag.toLowerCase().contains(keyword));
        }
        return null;
    }

    private OrderSpecifier<?> sortTypeEq(String sortType) {

        OrderSpecifier<?> orderSpecifier =
                switch (sortType) {
                    case "like" -> videoEntity.numOfLike.desc();
                    case "new" -> videoEntity.time.desc();
                    default -> videoEntity.time.desc();
                };

        return orderSpecifier;
    }
}
