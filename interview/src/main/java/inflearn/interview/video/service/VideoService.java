package inflearn.interview.video.service;

import inflearn.interview.common.service.S3Service;
import inflearn.interview.question.domain.Question;
import inflearn.interview.question.service.QuestionRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.video.controller.response.VideoDetailResponse;
import inflearn.interview.video.domain.*;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.common.exception.RequestDeniedException;
import inflearn.interview.videolike.domain.VideoLike;
import inflearn.interview.videolike.service.VideoLikeRepository;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import inflearn.interview.videoquestion.infrastructure.VideoQuestionEntity;
import inflearn.interview.videoquestion.service.VideoQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoLikeRepository videoLikeRepository;
    private final UserRepository userRepository;
    private final VideoQuestionRepository videoQuestionRepository;
    private final QuestionRepository questionRepository;
    private final S3Service s3Service;
    private final CustomVideoRepository customVideoRepository;

    public Video getById(Long id) {
        return videoRepository.findById(id).orElseThrow(OptionalNotFoundException::new);
    }

    public VideoDetailResponse getVideoById(Long videoId, User user){

        Video video = getById(videoId);
        if (video.getExposure()) {
            Optional<VideoLike> videoLike = videoLikeRepository.findByUserAndVideo(user, video);
            return VideoDetailResponse.from(video, user, videoLike.isPresent());
        }
        else {
            if (user.equals(video.getUser())) {
                Optional<VideoLike> videoLike = videoLikeRepository.findByUserAndVideo(user, video);
                return VideoDetailResponse.from(video, user, videoLike.isPresent());
            }
            throw new RequestDeniedException();
        }
    }

    public void update(VideoUpdate videoUpdate){
        Video video = getById(videoUpdate.getVideoId());
        video = video.update(videoUpdate);
        videoRepository.save(video);
    }

    public void delete(VideoDelete videoDelete){
        Video video = getById(videoDelete.getVideoId());
        videoRepository.delete(video);
        s3Service.deleteVideo(video.getVideoLink(), video.getThumbnailLink());
    }

    //정렬 : 최신순, 좋아요순, 댓글순

    public Page<VideoDTO2> getVideoList(String sortType, String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 6);
        return customVideoRepository.findAllVideoByPageInfo(sortType, keyword, pageRequest);
    }


    public Long create(VideoCreate videoCreate) {
        User user = userRepository.findById(videoCreate.getUserId()).orElseThrow(OptionalNotFoundException::new);
        Video video = Video.from(videoCreate, user);
        video = videoRepository.save(video);
        Long[] questions = videoCreate.getQuestions();
        for (Long question : questions) {
            Question getQuestion = questionRepository.findById(question).orElseThrow(OptionalNotFoundException::new);
            videoQuestionRepository.save(VideoQuestion.from(video, getQuestion));
        }
        return video.getId();
    }
}