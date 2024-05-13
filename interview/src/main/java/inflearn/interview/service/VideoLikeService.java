package inflearn.interview.service;

import inflearn.interview.domain.dao.UserDAO;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.domain.dao.VideoLikeDAO;
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

    public void addLike(Long video_id, UserDAO userDAO){
        UserDAO user = userRepository.findById(userDAO.getUserId()).get();
        VideoDAO video = videoRepository.findById(video_id).get();
        VideoLikeDAO videoLike = new VideoLikeDAO();
        videoLike.setVideo(video);
        videoLike.setUser(user);
        videoLike.setTime(LocalDateTime.now());

        videoLikeRepository.save(videoLike);
    }

    public void deleteLike(Long video_id, UserDAO userDAO){
        VideoDAO video = videoRepository.findById(video_id).get();
        UserDAO user = userRepository.findById(userDAO.getUserId()).get();
        videoLikeRepository.deleteByUserAndVideo(user, video);
    }
}