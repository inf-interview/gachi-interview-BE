package inflearn.interview.video.service;

import inflearn.interview.video.controller.response.MyVideoResponse;
import inflearn.interview.video.domain.Video;

import java.util.List;
import java.util.Optional;

public interface VideoRepository {

    Video save(Video video);

    Optional<Video> findById(Long id);

    void delete(Video video);

    List<MyVideoResponse> findMyVideo(Long userId);
}