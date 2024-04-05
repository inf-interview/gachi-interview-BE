package inflearn.interview.converter;

import inflearn.interview.domain.dao.UserDAO;
import inflearn.interview.domain.dao.VideoCommentDAO;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoCommentDTOToDAOConverter implements Converter<VideoCommentDTO, VideoCommentDAO> {
    private final VideoRepository videoRepository;
    private final UserRepository  userRepository;
    @Override
    public VideoCommentDAO convert(VideoCommentDTO source) {
        VideoCommentDAO target = new VideoCommentDAO();
        target.setId(source.getCommentId());
        target.setContent(source.getContent());
        target.setTime(source.getTime());
        target.setUpdatedTime(source.getUpdatedTime());
        VideoDAO video = videoRepository.findById(source.getVideoId()).get();
        UserDAO user = userRepository.findById(source.getUserId()).get();
        target.setVideo(video);
        target.setUser(user);
        return target;
    }
}
