package inflearn.interview.service;

import inflearn.interview.converter.VideoDAOToDTOConverter;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.dto.VideoDTO;
import inflearn.interview.repository.VideoLikeRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        Video video = videoRepository.findById(videoId).get();
        Long likeCount = videoLikeRepository.countAllByVideo(video);
        VideoDTO videoDTO = DAOToDTOConverter.convert(video);

        Objects.requireNonNull(videoDTO).setLike(likeCount);



        return videoDTO;
    }

    public void updateVideo(Long videoId, VideoDTO updatedVideo){
        Optional<Video> result = videoRepository.findById(videoId);
        Video originalVideo = result.get();
        updateVideoInformation(updatedVideo, originalVideo);
    }

    public void deleteVideo(Long videoId){
        videoRepository.deleteById(videoId);
    }

    //정렬 : 최신순, 좋아요순, 댓글순

    public Page<VideoDTO> getVideoList(int page, int size, String sortType, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);

        Sort sort = switch (sortType) {
            case "like" -> Sort.by(direction, "like");
            case "comment" -> Sort.by(direction, "commentCount");
            default -> Sort.by(direction, "time");
        };
        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<Video> videoPage = videoRepository.findAll(pageable);

        return videoPage.map(DAOToDTOConverter::convert);
    }





    private static void updateVideoInformation(VideoDTO updatedVideo, Video newVideo) {
        newVideo.setVideoLink(updatedVideo.getVideoLink());
        newVideo.setExposure(updatedVideo.getExposure());
        String[] tags = updatedVideo.getTags();
        StringBuilder rawTag = new StringBuilder();
        for (String tag : tags) {
            rawTag.append(tag).append(".");
        }
        newVideo.setTag(rawTag.toString());
        newVideo.setUpdatedTime(LocalDateTime.now());
    }


}