package inflearn.interview.service;

import inflearn.interview.converter.VideoDAOToDTOConverter;
import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoLike;
import inflearn.interview.domain.VideoQuestion;
import inflearn.interview.domain.dto.VideoDTO2;
import inflearn.interview.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoLikeRepository videoLikeRepository;
    private final VideoDAOToDTOConverter DAOToDTOConverter;
    private final UserRepository userRepository;
    private final VideoQuestionRepository videoQuestionRepository;
    private final QuestionRepository questionRepository;

    public VideoDTO2 getVideoById(Long videoId, User user){

        Video video = videoRepository.findById(videoId).get();
        VideoDTO2 videoDTO = new VideoDTO2(video);

        Optional<VideoLike> vl = videoLikeRepository.findByUserAndVideo(user, video);
        videoDTO.setLiked(vl.isPresent());
        return videoDTO;

    }

    public void updateVideo(Long videoId, VideoDTO2 updatedVideo){
        Video originalVideo = videoRepository.findById(videoId).get();
        originalVideo.setExposure(updatedVideo.isExposure());
        originalVideo.setVideoTitle(updatedVideo.getVideoTitle());
        originalVideo.setTag(dtoToEntityConverter(updatedVideo.getTags()));
    }

    public void deleteVideo(Long videoId){
        videoRepository.deleteById(videoId);
    }

    //정렬 : 최신순, 좋아요순, 댓글순

    public Page<VideoDTO2> getVideoList(String sortType, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return videoRepository.findAllVideoByPageInfo(sortType, pageRequest);
//        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
//
//        Sort sort = switch (sortType) {
//            case "like" -> Sort.by(direction, "like");
//            case "comment" -> Sort.by(direction, "commentCount");
//            default -> Sort.by(direction, "time");
//        };
//        PageRequest pageable = PageRequest.of(page, size, sort);
//
//        Page<Video> videoPage = videoRepository.findAll(pageable);
//
//        return videoPage.map(DAOToDTOConverter::convert);
    }





    private String dtoToEntityConverter(String[] tags) {
        StringBuilder rawTag = new StringBuilder();
        for (String tag : tags) {
            rawTag.append(tag).append(".");
        }
        return rawTag.toString();
    }


    public Long completeVideo(VideoDTO2 videoDTO) {
        User user = userRepository.findById(videoDTO.getUserId()).get();
        Video video = new Video();
        video.setUser(user);
        video.setExposure(videoDTO.isExposure());
        video.setVideoLink(videoDTO.getVideoLink());
        video.setVideoTitle(videoDTO.getVideoTitle());
        String[] tags = videoDTO.getTags();
        StringBuilder rawTag = new StringBuilder();
        for (String tag : tags) {
            rawTag.append(tag).append(".");
        }
        video.setTag(rawTag.toString());
        video.setThumbnailLink(videoDTO.getThumbnailLink());
        video.setTime(LocalDateTime.now());
        Video saved = videoRepository.save(video);

        Long[] questions = videoDTO.getQuestions();
        for (Long question : questions) {
            VideoQuestion videoQuestion = new VideoQuestion(video, questionRepository.findById(question).get());
            videoQuestionRepository.save(videoQuestion);
        }

        return saved.getVideoId();

    }
}