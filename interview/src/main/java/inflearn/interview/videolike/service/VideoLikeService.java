package inflearn.interview.videolike.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.Video;
import inflearn.interview.videolike.domain.VideoLike;
import inflearn.interview.common.domain.LikeDTO;
import inflearn.interview.video.domain.VideoDTO2;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.video.service.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoLikeService {
    private final VideoLikeRepository videoLikeRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public LikeDTO addLike(Long video_id, VideoDTO2 videoDTO2){
        Video video = videoRepository.findById(video_id).orElseThrow(OptionalNotFoundException::new);
        User user = userRepository.findById(videoDTO2.getUserId()).orElseThrow(OptionalNotFoundException::new);
        Optional<VideoLike> getLike = videoLikeRepository.findByUserAndVideo(user, video);
        if(getLike.isEmpty()){
            VideoLike videoLike = new VideoLike();
            videoLike.setVideo(video);
            videoLike.setUser(user);
            videoLike.setTime(LocalDateTime.now());
            video.setNumOfLike(video.getNumOfLike() + 1);

            videoLikeRepository.save(videoLike);
            return new LikeDTO(video.getNumOfLike(), true);
        }
        else{
            VideoLike videoLike = getLike.get();
            videoLikeRepository.delete(videoLike);
            video.setNumOfLike(video.getNumOfLike() - 1);
            return new LikeDTO(video.getNumOfLike(), false);
        }

    }
}