package inflearn.interview.service;

import inflearn.interview.converter.VideoCommentDTOToDAOConverter;
import inflearn.interview.domain.dao.VideoCommentDAO;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoCommentRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoCommentService {
    private final VideoRepository videoRepository;
    private final VideoCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final VideoCommentDTOToDAOConverter commentConverter;
    public List<VideoCommentDAO> getComments(Long videoId) {
        return videoRepository.findById(videoId).get().getComments();
    }

    public VideoCommentDAO getComment(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Transactional
    public VideoCommentDAO addComment(Long videoId, VideoCommentDTO comment) {
        String userName = userRepository.findById(comment.getUserId()).get().getName();
        comment.setUserName(userName);
        comment.setVideoId(videoId);
        comment.setTime(LocalDateTime.now());
        VideoCommentDAO commentDAO = commentConverter.convert(comment);
        VideoCommentDAO saved = commentRepository.save(commentDAO);
        return saved;
    }

    @Transactional
    public void editComment(Long commentId, VideoCommentDTO videoCommentDTO) {
        VideoCommentDAO comment = commentRepository.findById(commentId).get();
        comment.setContent(videoCommentDTO.getContent());
    }

    public void deleteComment(Long commentId, VideoCommentDTO videoCommentDTO) {
        VideoCommentDAO comment = commentRepository.findById(commentId).get();
        if (!comment.getUser().getUserId().equals(videoCommentDTO.getUserId())) {
            return;
        }
        commentRepository.delete(comment);
    }
}
