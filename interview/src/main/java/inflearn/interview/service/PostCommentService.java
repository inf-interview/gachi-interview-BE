package inflearn.interview.service;

import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessagingException;
import inflearn.interview.domain.Post;
import inflearn.interview.domain.PostComment;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.exception.OptionalNotFoundException;
import inflearn.interview.exception.RequestDeniedException;
import inflearn.interview.repository.PostCommentRepository;
import inflearn.interview.repository.PostRepository;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public PostCommentDTO getComment(Long commentId) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);

        PostCommentDTO returnDto = new PostCommentDTO();
        returnDto.setCommentId(postComment.getPostCommentId());
        returnDto.setUserId(postComment.getUser().getUserId());
        returnDto.setUsername(postComment.getUser().getName());
        returnDto.setContent(postComment.getContent());
        return returnDto;
    }

    public List<PostCommentDTO> getComments(Long postId) {
        List<PostComment> commentList = postCommentRepository.findCommentList(postId);
        List<PostCommentDTO> commentDTOS = new ArrayList<>();

        for (PostComment postComment : commentList) {
            PostCommentDTO postCommentDTO = new PostCommentDTO();
            postCommentDTO.setCommentId(postComment.getPostCommentId());
            postCommentDTO.setUserId(postComment.getUser().getUserId());
            postCommentDTO.setUsername(postComment.getUser().getName());
            postCommentDTO.setImage(postComment.getUser().getImage());
            postCommentDTO.setContent(postComment.getContent());
            postCommentDTO.setCreatedAt(postComment.getCreatedAt());
            commentDTOS.add(postCommentDTO);
        }
        return commentDTOS;
    }

    public PostCommentDTO createComment(PostCommentDTO postCommentDTO, Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(OptionalNotFoundException::new);
        User findUser = userRepository.findById(postCommentDTO.getUserId()).orElseThrow(OptionalNotFoundException::new);
        PostComment postComment = new PostComment(findUser, findPost, postCommentDTO.getContent());
        PostComment saved = postCommentRepository.save(postComment);

        PostCommentDTO returnDto = new PostCommentDTO();
        returnDto.setCommentId(postComment.getPostCommentId());
        returnDto.setUserId(postComment.getUser().getUserId());
        returnDto.setUsername(postComment.getUser().getName());
        returnDto.setContent(postComment.getContent());
        returnDto.setCreatedAt(postComment.getCreatedAt());

        if (!(findPost.getUser().getUserId().equals(postCommentDTO.getUserId()))) {
            try {
                fcmTokenService.commentSendNotification(saved.getPost().getUser().getUserId(), saved.getPost().getTitle(), saved.getUser().getName());
            } catch (Exception e) {
                log.error("PostComment 알림 전송 실패 = {}", e.getMessage());
            }
        }

        return returnDto;

    }



    public void updateComment(Long postId, Long commentId, PostCommentDTO postCommentDTO) {
        PostComment findComment = postCommentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);

        if (!(findComment.getUser().getUserId()).equals(postCommentDTO.getUserId())) {
            throw new RequestDeniedException();
        }

        findComment.setContent(postCommentDTO.getContent());
        findComment.setUpdatedAt(LocalDateTime.now());
    }

    public void deleteComment(Long postId, Long commentId, PostCommentDTO postCommentDTO) {
        PostComment findComment = postCommentRepository.findById(commentId).orElseThrow(OptionalNotFoundException::new);

        if (!(findComment.getUser().getUserId()).equals(postCommentDTO.getUserId())) {
            throw new RequestDeniedException();
        }

        postCommentRepository.delete(findComment);
    }
}
