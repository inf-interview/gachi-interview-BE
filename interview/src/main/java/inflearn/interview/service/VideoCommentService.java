package inflearn.interview.service;

import inflearn.interview.converter.VideoCommentDAOToDTOConverter;
import inflearn.interview.converter.VideoCommentDTOToDAOConverter;
import inflearn.interview.domain.VideoComment;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoCommentRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoCommentService {
    private final VideoRepository videoRepository;
    private final VideoCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final VideoCommentDTOToDAOConverter commentConverter;
    private final VideoCommentDAOToDTOConverter converter;

    public List<VideoCommentDTO> getComments(Long videoId) {
        List<VideoComment> comments = videoRepository.findById(videoId).get().getComments();
        List<VideoCommentDTO> results = new ArrayList<>();
        for (VideoComment comment : comments) {
            results.add(converter.convert(comment));
        }
        return results;
    }

    public VideoCommentDTO getComment(Long commentId) {
        VideoComment comment = commentRepository.findById(commentId).get();
        return converter.convert(comment);
    }

    public void addComment(Long videoId, VideoCommentDTO comment) {
        commentRepository.save(Objects.requireNonNull(commentConverter.convert(comment)));
    }

    public void editComment(Long commentId, VideoCommentDTO videoCommentDTO) {
        VideoComment target = commentRepository.findById(commentId).get();
        target.setUpdatedTime(LocalDateTime.now());
        target.setContent(videoCommentDTO.getContent());
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
