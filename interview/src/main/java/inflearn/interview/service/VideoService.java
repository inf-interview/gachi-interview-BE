package inflearn.interview.service;

import inflearn.interview.converter.VideoDAOToDTOConverter;
import inflearn.interview.converter.VideoDTOToDAOConverter;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.domain.dto.VideoDTO;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoDAOToDTOConverter DAOToDTOConverter;
    private final VideoDTOToDAOConverter DTOToDAOConverter;

    public VideoDTO getVideoById(Long videoId){
        VideoDTO videoDTO;
        Optional<VideoDAO> result = videoRepository.findById(videoId);
        videoDTO = DAOToDTOConverter.convert(result.get());

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


















    private static void updateVideoInformation(VideoDTO updatedVideo, VideoDAO originalVideo) {
        originalVideo.setVideoLink(updatedVideo.getVideoLink());
        originalVideo.setExposure(updatedVideo.getExposure());
        String[] tags = updatedVideo.getTags();
        StringBuilder rawTag = new StringBuilder();
        for (String tag : tags) {
            rawTag.append(tag).append(".");
        }
        originalVideo.setRawTags(rawTag.toString());
    }
}
