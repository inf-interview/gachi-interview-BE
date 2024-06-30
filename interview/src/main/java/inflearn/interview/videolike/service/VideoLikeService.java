package inflearn.interview.videolike.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.video.domain.Video;
import inflearn.interview.videolike.controller.response.LikeResponse;
import inflearn.interview.videolike.domain.VideoLike;
import inflearn.interview.videolike.domain.VideoLikeRequest;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.video.service.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoLikeService {
    private final VideoLikeRepository videoLikeRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public LikeResponse addLike(VideoLikeRequest videoLikeRequest){
        Video video = videoRepository.findById(videoLikeRequest.getVideoId()).orElseThrow(OptionalNotFoundException::new);
        User user = userRepository.findById(videoLikeRequest.getUserId()).orElseThrow(OptionalNotFoundException::new);
        Optional<VideoLike> getLike = videoLikeRepository.findVideoLike(videoLikeRequest.getUserId(), videoLikeRequest.getVideoId());

        if (getLike.isEmpty()) {
            VideoLike videoLike = VideoLike.from(user, video);
            videoLikeRepository.save(videoLike);
            Video likedVideo = video.plusLike();
            videoRepository.save(likedVideo);
            return LikeResponse.from(true, likedVideo);

        } else {
            videoLikeRepository.delete(getLike.get());
            Video unLikedVideo = video.minusLike();
            videoRepository.save(unLikedVideo);

            return LikeResponse.from(false, unLikedVideo);
        }

    }
}