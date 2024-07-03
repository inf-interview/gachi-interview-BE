package inflearn.interview.video.service;

import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.user.domain.User;
import inflearn.interview.video.controller.response.MyVideoResponse;
import inflearn.interview.video.controller.response.VideoDetailResponse;
import inflearn.interview.video.domain.*;
import inflearn.interview.videocomment.service.VideoCommentRepository;
import inflearn.interview.videolike.service.VideoLikeRepository;
import inflearn.interview.videoquestion.service.VideoQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeVideoService implements VideoService{

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoCommentRepository videoCommentRepository;

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    @Autowired
    private VideoQuestionRepository videoQuestionRepository;

    @Override
    public Video getById(Long id) {
        return videoRepository.findById(id).orElseThrow(OptionalNotFoundException::new);
    }

    @Override
    public VideoDetailResponse getVideoById(Long videoId, User user) {
        return null;
    }

    @Override
    public void update(VideoUpdate videoUpdate) {

    }

    @Override
    public void delete(VideoDelete videoDelete) {
        Video video = getById(videoDelete.getVideoId());
        videoLikeRepository.deleteByVideo(video);
        videoQuestionRepository.deleteByVideo(video);
        videoCommentRepository.deleteByVideo(video);
        videoRepository.delete(video);
    }

    @Override
    public Page<VideoDTO2> getVideoList(String sortType, String keyword, int page) {
        return null;
    }

    @Override
    public Long create(VideoCreate videoCreate) {
        return null;
    }

    @Override
    public List<MyVideoResponse> getMyVideo(Long userId) {
        return null;
    }
}
