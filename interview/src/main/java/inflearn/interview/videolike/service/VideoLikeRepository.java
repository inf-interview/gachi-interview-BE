package inflearn.interview.videolike.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.domain.Video;
import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videolike.domain.VideoLike;
import inflearn.interview.videolike.infrastructure.VideoLikeEntity;

import java.util.Optional;

public interface VideoLikeRepository {

    Optional<VideoLike> findByUserAndVideo(User user, Video video);

    VideoLike save(VideoLike videoLike);

    void delete(VideoLike videoLike);
}
