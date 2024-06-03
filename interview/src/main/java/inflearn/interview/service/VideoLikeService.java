package inflearn.interview.service;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoLike;
import inflearn.interview.domain.dto.LikeDTO;
import inflearn.interview.domain.dto.VideoDTO2;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoLikeRepository;
import inflearn.interview.repository.VideoRepository;
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
        Video video = videoRepository.findById(video_id).get();
        User user = userRepository.findById(videoDTO2.getUserId()).get();
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