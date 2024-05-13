package inflearn.interview.service;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.PostComment;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.exception.RequestDeniedException;
import inflearn.interview.repository.PostCommentRepository;
import inflearn.interview.repository.PostRepository;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostCommentDTO getComment(Long commentId) {
        PostComment postComment = postCommentRepository.findById(commentId).get();

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
            postCommentDTO.setUserId(postComment.getUser().getUserId());
            postCommentDTO.setUsername(postComment.getUser().getName());
            postCommentDTO.setContent(postComment.getContent());
            commentDTOS.add(postCommentDTO);
        }
        return commentDTOS;
    }

    public PostCommentDTO createComment(PostCommentDTO postCommentDTO, Long postId) {
        Post findPost = postRepository.findById(postId).get();
        User findUser = userRepository.findById(postCommentDTO.getUserId()).get();
        PostComment postComment = new PostComment(findUser, findPost, postCommentDTO.getContent());
        postCommentRepository.save(postComment);

        PostCommentDTO returnDto = new PostCommentDTO();
        returnDto.setCommentId(postComment.getPostCommentId());
        returnDto.setUserId(postComment.getUser().getUserId());
        returnDto.setUsername(postComment.getUser().getName());
        returnDto.setContent(postComment.getContent());
        returnDto.setCreatedAt(postComment.getCreatedAt());
        return returnDto;
    }

    public void updateComment(Long postId, Long commentId, PostCommentDTO postCommentDTO) {
        PostComment findComment = postCommentRepository.findById(commentId).get();

        if (!(findComment.getUser().getUserId()).equals(postCommentDTO.getUserId())) {
            throw new RequestDeniedException();
        }

        findComment.setContent(postCommentDTO.getContent());
        findComment.setUpdatedAt(LocalDateTime.now());
    }

    public void deleteComment(Long postId, Long commentId, PostCommentDTO postCommentDTO) {
        PostComment findComment = postCommentRepository.findById(commentId).get();

        if (!(findComment.getUser().getUserId()).equals(postCommentDTO.getUserId())) {
            throw new RequestDeniedException();
        }

        postCommentRepository.delete(findComment);
    }
}
