package inflearn.interview.videocomment.service;

import inflearn.interview.fcm.service.FcmTokenService;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.domain.Video;
import inflearn.interview.videocomment.domain.VideoComment;
import inflearn.interview.postcomment.domain.PostCommentDTO;
import inflearn.interview.videocomment.domain.VideoCommentDTO;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.common.exception.RequestDeniedException;
import inflearn.interview.video.service.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VideoCommentService {
    private final VideoCommentRepository commentRepository;
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
        results.sort(Comparator.comparing(PostCommentDTO::getCreatedAt).reversed());
        return results;
    }

    public VideoCommentDTO getComment(Long commentId) {
        VideoComment comment = commentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);
        return converter.convert(comment);
    }

    public void addComment(Long videoId, VideoCommentDTO comment) {
        VideoComment videoComment = new VideoComment();
        UserEntity userEntity = userRepository.findById(comment.getUserId()).orElseThrow(OptionalNotFoundException::new);
        Video video = videoRepository.findById(videoId).orElseThrow(OptionalNotFoundException::new);
        videoComment.setUserEntity(userEntity);
        videoComment.setVideo(video);
        videoComment.setTime(LocalDateTime.now());
        videoComment.setContent(comment.getContent());

        VideoComment saved = commentRepository.save(videoComment);

        if (!(video.getUserEntity().getUserId().equals(comment.getUserId()))) {
            try {
                fcmTokenService.commentSendNotification(saved.getVideo().getUserEntity().getUserId(), saved.getVideo().getVideoTitle(), saved.getUserEntity().getName());
            } catch (Exception e) {
                log.error("VideoComment 알림 전송 실패 = {}", e.getMessage());
            }
        }

    }

    public void editComment(Long commentId, VideoCommentDTO videoCommentDTO) {
        VideoComment target = commentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);
        if (target.getUserEntity().getUserId().equals(videoCommentDTO.getUserId())) {
            target.setUpdatedTime(LocalDateTime.now());
            target.setContent(videoCommentDTO.getContent());
        } else {
            throw new RequestDeniedException();
        }
    }

    public void deleteComment(Long commentId, VideoCommentDTO videoCommentDTO) {
        VideoComment target = commentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);
        if (target.getUserEntity().getUserId().equals(videoCommentDTO.getUserId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new RequestDeniedException();
        }
    }
}
