package inflearn.interview.video.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.video.controller.response.VideoDetailResponse;
import inflearn.interview.video.domain.*;
import org.springframework.data.domain.Page;

public interface VideoService {
    Video getById(Long id);

    VideoDetailResponse getVideoById(Long videoId, User user);

    void update(VideoUpdate videoUpdate);

    void delete(VideoDelete videoDelete);

    Page<VideoDTO2> getVideoList(String sortType, String keyword, int page);

    Long create(VideoCreate videoCreate);
}
