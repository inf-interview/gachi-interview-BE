package inflearn.interview.user.service;

import inflearn.interview.notice.domain.NoticeDTO;
import inflearn.interview.notice.service.NoticeRepository;
import inflearn.interview.post.domain.MyPostDTO;
import inflearn.interview.post.infrastructure.PostJpaRepository;
import inflearn.interview.postcomment.infrastructure.PostCommentEntity;
import inflearn.interview.postcomment.domain.PostCommentDTO;
import inflearn.interview.postcomment.service.PostCommentRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.MyVideoDTO;
import inflearn.interview.video.service.VideoRepository;
import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.videocomment.domain.VideoCommentDTO;
import inflearn.interview.videocomment.service.VideoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService { // TODO 수정필요

    private final UserRepository userRepository;
    private final PostJpaRepository postJpaRepository;
    private final PostCommentRepository postCommentRepository;
    private final VideoCommentRepository videoCommentRepository;
    private final NoticeRepository noticeRepository;
    private final VideoRepository videoRepository;


    public List<MyPostDTO> getMyPost(Long userId, String category) {
        return postJpaRepository.findPostByUserId(userId, category);
    }

    public List<MyVideoDTO> getMyVideo(Long userId) {
        return videoRepository.findVideoByUserId(userId);
    }


    public List<PostCommentDTO> getMyComment(Long userId) {
        List<PostCommentEntity> postCommentEntities = postCommentRepository.findCommentByUserId(userId);
        return postCommentEntities.stream().map(comment -> new PostCommentDTO(comment)).toList();
    }

    public List<VideoCommentDTO> getMyVideoComment(Long userId) {
        List<VideoCommentEntity> videoCommentEntities = videoCommentRepository.findCommentByUserId(userId);
        return videoCommentEntities.stream().map(comment -> new VideoCommentDTO(comment)).toList();
    }

    public List<NoticeDTO> getMyNotice(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        return noticeRepository.findByUser(user).stream().map(NoticeDTO::new).sorted(Comparator.comparing(NoticeDTO::getCreatedAt).reversed()).toList();
    }
}
