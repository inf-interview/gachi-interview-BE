package inflearn.interview.video.infrastructure;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inflearn.interview.domain.dto.QVideoDTO2;
import inflearn.interview.video.domain.VideoDTO2;
import inflearn.interview.video.service.CustomVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static inflearn.interview.domain.QVideo.video;
@Repository
@RequiredArgsConstructor
public class CustomVideoRepositoryImpl implements CustomVideoRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<VideoDTO2> findAllVideoByPageInfo(String sortType, String keyword, Pageable pageable) {

        List<VideoDTO2> result = jpaQueryFactory
                .select(new QVideoDTO2(
                        video.user.userId,
                        video.user.name,
                        video.videoId,
                        video.videoLink,
                        video.videoTitle,
                        video.time,
                        video.updatedTime,
                        video.numOfLike,
                        video.tag,
                        video.thumbnailLink,
                        video.user.image
                ))
                .from(video)
                .where(video.exposure)
                .where(keywordEq(keyword))
                .orderBy(sortTypeEq(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = jpaQueryFactory
                .select(video.count())
                .from(video)
                .where(video.exposure)
                .where(keywordEq(keyword))
                .fetchOne();

        total = (total != null) ? total : 0L;

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression keywordEq(String keyword) {
        if (!keyword.isEmpty()) {
            return video.videoTitle.toLowerCase().contains(keyword).or(video.tag.toLowerCase().contains(keyword));
        }
        return null;
    }

    private OrderSpecifier<?> sortTypeEq(String sortType) {

        OrderSpecifier<?> orderSpecifier =
                switch (sortType) {
                    case "like" -> video.numOfLike.desc();
                    case "new" -> video.time.desc();
                    default -> video.time.desc();
                };

        return orderSpecifier;
    }
}
