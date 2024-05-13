package inflearn.interview.converter;

import inflearn.interview.domain.dao.VideoCommentDAO;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.repository.VideoLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoCommentDAOToDTOConverter implements Converter< VideoCommentDAO, VideoCommentDTO> {
    private final VideoLikeRepository videoLikeRepository;
    @Override
    public VideoCommentDTO convert(VideoCommentDAO source) {
        VideoCommentDTO target = new VideoCommentDTO();
        target.setTime(source.getTime());
        target.setContent(source.getContent());
        target.setCommentId(source.getId());
        target.setVideoId(source.getVideo().getVideoId());
        target.setUserId(source.getUser().getUserId());
        target.setUpdatedTime(source.getUpdatedTime());
        target.setUserName(source.getUser().getName());
        target.setLike(videoLikeRepository.countAllByVideo(source.getVideo()));
        return target;
    }
}
