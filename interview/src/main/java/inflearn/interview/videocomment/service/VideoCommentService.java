package inflearn.interview.videocomment.service;

import inflearn.interview.fcm.service.FcmTokenService;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.video.domain.Video;
import inflearn.interview.videocomment.controller.response.VideoCommentListResponse;
import inflearn.interview.videocomment.domain.*;
import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;
import inflearn.interview.postcomment.domain.PostCommentDTO;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.common.exception.RequestDeniedException;
import inflearn.interview.video.service.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VideoCommentService {
    private final VideoCommentRepository commentRepository;
    private final FcmTokenService fcmTokenService;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    private VideoComment getById(Long id) {
        return commentRepository.findByid(id).orElseThrow(OptionalNotFoundException::new);
    }

    public List<VideoCommentListResponse> getComments(Long videoId) {
        List<VideoCommentEntity> commentList = commentRepository.findCommentList(videoId);
        List<VideoCommentListResponse> response = VideoCommentListResponse.from(commentList);
        response.sort(Comparator.comparing(VideoCommentListResponse::getCreatedAt).reversed());
        return response;
    }

    public void create(Long videoId, VideoCommentCreate videoCommentCreate) {
        User user = userRepository.findById(videoCommentCreate.getUserId()).orElseThrow(OptionalNotFoundException::new);
        Video video = videoRepository.findById(videoId).orElseThrow(OptionalNotFoundException::new);
        VideoComment videoComment = VideoComment.from(videoCommentCreate, user, video);
        videoComment = commentRepository.save(videoComment);

        //TODO fcm 수정필요
        if (!(videoEntity.getUserEntity().getUserId().equals(comment.getUserId()))) {
            try {
                fcmTokenService.commentSendNotification(saved.getVideoEntity().getUserEntity().getUserId(), saved.getVideoEntity().getVideoTitle(), saved.getUserEntity().getName());
            } catch (Exception e) {
                log.error("VideoComment 알림 전송 실패 = {}", e.getMessage());
            }
        }

    }

    public void update(VideoCommentUpdate videoCommentUpdate) {
        VideoComment videoComment = getById(videoCommentUpdate.getCommentId());
        videoComment = videoComment.update(videoCommentUpdate);
        commentRepository.save(videoComment);
    }

    public void delete(VideoCommentDelete videoCommentDelete) {
        VideoComment videoComment = getById(videoCommentDelete.getCommentId());
        commentRepository.delete(videoComment);
    }
}
