package inflearn.interview.video.service;

import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.user.domain.User;
import inflearn.interview.video.controller.response.VideoDetailResponse;
import inflearn.interview.video.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FakeVideoService implements VideoService{

    @Autowired
    private VideoRepository videoRepository;

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
}
