package inflearn.interview.video.service;

import inflearn.interview.video.domain.VideoDTO2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomVideoRepository {

    Page<VideoDTO2> findAllVideoByPageInfo(String sortType, String keyword, Pageable pageable);
}
