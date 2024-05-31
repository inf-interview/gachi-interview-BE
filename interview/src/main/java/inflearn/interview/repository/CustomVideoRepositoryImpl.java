package inflearn.interview.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inflearn.interview.domain.dto.QVideoDTO2;
import inflearn.interview.domain.dto.VideoDTO2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static inflearn.interview.domain.QVideo.video;
@Repository
@RequiredArgsConstructor
public class CustomVideoRepositoryImpl implements CustomVideoRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<VideoDTO2> findAllVideoByPageInfo(String sortType, Pageable pageable) {

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
                .orderBy(sortTypeEq(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        int total = jpaQueryFactory
                .select(video.count())
                .from(video)
                .fetch()
                .size();

        return new PageImpl<>(result, pageable, total);
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
