package inflearn.interview.converter;

import inflearn.interview.domain.User;
import inflearn.interview.domain.VideoComment;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoCommentDTOToDAOConverter implements Converter<VideoCommentDTO, VideoComment> {
    private final VideoRepository videoRepository;
    private final UserRepository  userRepository;
    @Override
    public VideoComment convert(VideoCommentDTO source) {
        VideoComment target = new VideoComment();
        target.setId(source.getCommentId());
        target.setContent(source.getContent());
        target.setTime(source.getTime());
        target.setUpdatedTime(source.getUpdatedTime());
        Video video = videoRepository.findById(source.getVideoId()).get();
        User user = userRepository.findById(source.getUserId()).get();
        target.setVideo(video);
        target.setUser(user);
        return target;
    }
}
