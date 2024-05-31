package inflearn.interview.service;

import inflearn.interview.converter.VideoCommentDAOToDTOConverter;
import inflearn.interview.converter.VideoCommentDTOToDAOConverter;
import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoComment;
import inflearn.interview.domain.dto.PostCommentDTO;
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
    private final VideoCommentRepository commentRepository;
    private final VideoCommentDTOToDAOConverter commentConverter;
    private final VideoCommentDAOToDTOConverter converter;
    private final FcmTokenService fcmTokenService;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public List<PostCommentDTO> getComments(Long videoId) {
        List<VideoComment> commentList = commentRepository.findCommentList(videoId);
        List<PostCommentDTO> results = new ArrayList<>();
        for (VideoComment videoComment : commentList) {
            PostCommentDTO postCommentDTO = new PostCommentDTO(videoComment);
            results.add(postCommentDTO);
        }
        return results;
    }

    public VideoCommentDTO getComment(Long commentId) {
        VideoComment comment = commentRepository.findById(commentId).get();
        return converter.convert(comment);
    }

    public void addComment(Long videoId, VideoCommentDTO comment) {
        VideoComment videoComment = new VideoComment();
        User user = userRepository.findById(comment.getUserId()).get();
        Video video = videoRepository.findById(videoId).get();
        videoComment.setUser(user);
        videoComment.setVideo(video);
        videoComment.setTime(LocalDateTime.now());
        videoComment.setContent(comment.getContent());

        VideoComment saved = commentRepository.save(videoComment);
        fcmTokenService.commentSendNotification(saved.getVideo().getUser().getUserId(), saved.getVideo().getVideoTitle(), saved.getUser().getName());
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
