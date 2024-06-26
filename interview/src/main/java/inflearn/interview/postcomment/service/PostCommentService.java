package inflearn.interview.postcomment.service;

import inflearn.interview.fcm.service.FcmTokenService;
import inflearn.interview.post.domain.Post;
import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.postcomment.domain.PostCommentDTO;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.common.exception.RequestDeniedException;
import inflearn.interview.post.infrastructure.PostJpaRepository;
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
    private final PostJpaRepository postJpaRepository;
    private final UserRepository userRepository;
    private final FcmTokenService fcmTokenService;

    public PostCommentDTO getComment(Long commentId) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);

        PostCommentDTO returnDto = new PostCommentDTO();
        returnDto.setCommentId(postComment.getPostCommentId());
        returnDto.setUserId(postComment.getUserEntity().getUserId());
        returnDto.setUsername(postComment.getUserEntity().getName());
        returnDto.setContent(postComment.getContent());
        return returnDto;
    }

    public List<PostCommentDTO> getComments(Long postId) {
        List<PostComment> commentList = postCommentRepository.findCommentList(postId);
        List<PostCommentDTO> commentDTOS = new ArrayList<>();

        for (PostComment postComment : commentList) {
            PostCommentDTO postCommentDTO = new PostCommentDTO();
            postCommentDTO.setCommentId(postComment.getPostCommentId());
            postCommentDTO.setUserId(postComment.getUserEntity().getUserId());
            postCommentDTO.setUsername(postComment.getUserEntity().getName());
            postCommentDTO.setImage(postComment.getUserEntity().getImage());
            postCommentDTO.setContent(postComment.getContent());
            postCommentDTO.setCreatedAt(postComment.getCreatedAt());
            commentDTOS.add(postCommentDTO);
        }
        commentDTOS.sort(Comparator.comparing(PostCommentDTO::getCreatedAt).reversed());
        return commentDTOS;
    }

    public PostCommentDTO createComment(PostCommentDTO postCommentDTO, Long postId) {
        Post findPost = postJpaRepository.findById(postId).orElseThrow(OptionalNotFoundException::new);
        UserEntity findUserEntity = userRepository.findById(postCommentDTO.getUserId()).orElseThrow(OptionalNotFoundException::new);
        PostComment postComment = new PostComment(findUserEntity, findPost, postCommentDTO.getContent());
        PostComment saved = postCommentRepository.save(postComment);

        PostCommentDTO returnDto = new PostCommentDTO();
        returnDto.setCommentId(postComment.getPostCommentId());
        returnDto.setUserId(postComment.getUserEntity().getUserId());
        returnDto.setUsername(postComment.getUserEntity().getName());
        returnDto.setContent(postComment.getContent());
        returnDto.setCreatedAt(postComment.getCreatedAt());

        if (!(findPost.getUserEntity().getUserId().equals(postCommentDTO.getUserId()))) {
            try {
                fcmTokenService.commentSendNotification(saved.getPost().getUserEntity().getUserId(), saved.getPost().getTitle(), saved.getUserEntity().getName());
            } catch (Exception e) {
                log.error("PostComment 알림 전송 실패 = {}", e.getMessage());
            }
        }

        return returnDto;

    }



    public void updateComment(Long postId, Long commentId, PostCommentDTO postCommentDTO) {
        PostComment findComment = postCommentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);

        if (!(findComment.getUserEntity().getUserId()).equals(postCommentDTO.getUserId())) {
            throw new RequestDeniedException();
        }

        findComment.setContent(postCommentDTO.getContent());
        findComment.setUpdatedAt(LocalDateTime.now());
    }

    public void deleteComment(Long postId, Long commentId, PostCommentDTO postCommentDTO) {
        PostComment findComment = postCommentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);

        if (!(findComment.getUserEntity().getUserId()).equals(postCommentDTO.getUserId())) {
            throw new RequestDeniedException();
        }

        postCommentRepository.delete(findComment);
    }
}
