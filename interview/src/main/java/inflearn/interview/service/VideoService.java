package inflearn.interview.service;

import inflearn.interview.converter.VideoDAOToDTOConverter;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.domain.dto.VideoDTO;
import inflearn.interview.repository.VideoLikeRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoLikeRepository videoLikeRepository;
    private final VideoDAOToDTOConverter DAOToDTOConverter;

    public VideoDTO getVideoById(Long videoId){

        VideoDAO video = videoRepository.findById(videoId).get();
        Long likeCount = videoLikeRepository.countAllByVideo(video);
        VideoDTO videoDTO = DAOToDTOConverter.convert(video);

        Objects.requireNonNull(videoDTO).setLike(likeCount);



        return videoDTO;
    }

    public void updateVideo(Long videoId, VideoDTO updatedVideo){
        Optional<VideoDAO> result = videoRepository.findById(videoId);
        VideoDAO originalVideo = result.get();
        updateVideoInformation(updatedVideo, originalVideo);
    }

    public void deleteVideo(Long videoId){
        videoRepository.deleteById(videoId);
    }










    private static void updateVideoInformation(VideoDTO updatedVideo, VideoDAO newVideo) {
        newVideo.setVideoLink(updatedVideo.getVideoLink());
        newVideo.setExposure(updatedVideo.getExposure());
        String[] tags = updatedVideo.getTags();
        StringBuilder rawTag = new StringBuilder();
        for (String tag : tags) {
            rawTag.append(tag).append(".");
        }
        newVideo.setRawTags(rawTag.toString());
        newVideo.setUpdatedTime(LocalDateTime.now());
    }

    public Page<VideoDTO> getVideoList(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<VideoDAO> videoPage = videoRepository.findAll(pageable);

        return videoPage.map(DAOToDTOConverter::convert);
    }
}