package inflearn.interview.repository;

import inflearn.interview.domain.dto.VideoDTO2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomVideoRepository {

    Page<VideoDTO2> findAllVideoByPageInfo(String sortType, String keyword, Pageable pageable);
}
