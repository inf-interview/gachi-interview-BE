package inflearn.interview.videolike.service;

import inflearn.interview.video.domain.Video;
import inflearn.interview.videolike.domain.VideoLike;

import java.util.Optional;

public interface VideoLikeRepository {

    Optional<VideoLike> findVideoLike(Long userId, Long videoId);

    VideoLike save(VideoLike videoLike);

    void delete(VideoLike videoLike);

    void deleteByVideo(Video video);
}
