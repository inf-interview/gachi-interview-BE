package inflearn.interview.postcomment.service;

import inflearn.interview.fcm.service.FcmTokenService;
import inflearn.interview.post.domain.Post;
import inflearn.interview.post.service.PostRepository;
import inflearn.interview.postcomment.controller.response.PostCommentCreateResponse;
import inflearn.interview.postcomment.controller.response.PostCommentListResponse;
import inflearn.interview.postcomment.domain.*;
import inflearn.interview.postcomment.infrastructure.PostCommentEntity;
import inflearn.interview.user.domain.User;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.common.exception.RequestDeniedException;
import inflearn.interview.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FcmTokenService fcmTokenService;

    private PostComment getById(Long id) {
        return postCommentRepository.findById(id).orElseThrow(OptionalNotFoundException::new);
    }

    public List<PostCommentListResponse> getComments(Long postId) {
        List<PostCommentEntity> commentList = postCommentRepository.findCommentList(postId);
        List<PostCommentListResponse> response = PostCommentListResponse.from(commentList);
        response.sort(Comparator.comparing(PostCommentListResponse::getCreatedAt).reversed());
        return response;
    }

    public PostCommentCreateResponse createComment(PostCommentCreate postCommentCreate, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(OptionalNotFoundException::new);
        User user = userRepository.findById(postCommentCreate.getUserId()).orElseThrow(OptionalNotFoundException::new);
        PostComment postComment = PostComment.from(postCommentCreate, post, user);
        postComment = postCommentRepository.save(postComment);
        return PostCommentCreateResponse.from(postComment, user);


        //TODO 알림 수정 필요
        if (!(post.getUserEntity().getUserId().equals(postCommentDTO.getUserId()))) {
            try {
                fcmTokenService.commentSendNotification(saved.getPost().getUserEntity().getUserId(), saved.getPost().getTitle(), saved.getUserEntity().getName());
            } catch (Exception e) {
                log.error("PostComment 알림 전송 실패 = {}", e.getMessage());
            }
        }

        return returnDto;

    }



    public void updateComment(PostCommentUpdate postCommentUpdate) {
        PostComment postComment = getById(postCommentUpdate.getCommentId());
        postComment = postComment.update(postCommentUpdate);
        postCommentRepository.save(postComment);
    }

    public void deleteComment(PostCommentDelete postCommentDelete) {
        PostComment postComment = getById(postCommentDelete.getCommentId());
        postCommentRepository.delete(postComment);
    }
}
