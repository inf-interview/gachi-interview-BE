package inflearn.interview.service;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoLike;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoLikeRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoLikeService {
    private final VideoLikeRepository videoLikeRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public void addLike(Long video_id, User userDAO){
        Video video = videoRepository.findById(video_id).get();
        User user = userRepository.findById(userDAO.getUserId()).get();
        VideoLike findResult = videoLikeRepository.findByUserAndVideo(user, video);
        if(findResult == null){
            VideoLike videoLike = new VideoLike();
            videoLike.setVideo(video);
            videoLike.setUser(user);
            videoLike.setTime(LocalDateTime.now());

            videoLikeRepository.save(videoLike);
        }
        else{
            videoLikeRepository.deleteByUserAndVideo(user, video);
        }


    }
}