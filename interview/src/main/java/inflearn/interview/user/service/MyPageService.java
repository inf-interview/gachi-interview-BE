package inflearn.interview.user.service;

import inflearn.interview.notice.controller.response.NoticeResponse;
import inflearn.interview.notice.infrastructure.NoticeRepository;
import inflearn.interview.post.controller.response.MyPostResponse;
import inflearn.interview.post.service.PostRepository;
import inflearn.interview.postcomment.controller.response.MyPostCommentResponse;
import inflearn.interview.postcomment.infrastructure.PostCommentEntity;
import inflearn.interview.postcomment.service.PostCommentRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.controller.response.MyVideoResponse;
import inflearn.interview.video.service.VideoRepository;
import inflearn.interview.videocomment.controller.response.MyVideoCommentResponse;
import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.videocomment.service.VideoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final VideoCommentRepository videoCommentRepository;
    private final NoticeRepository noticeRepository;
    private final VideoRepository videoRepository;


    public List<MyPostResponse> getMyPost(Long userId, String category) {
        return postRepository.findMyPost(userId, category);
    }

    public List<MyVideoResponse> getMyVideo(Long userId) {
        return videoRepository.findMyVideo(userId);
    }


    public List<MyPostCommentResponse> getMyComment(Long userId) {
        List<PostCommentEntity> findMyComment = postCommentRepository.findMyComment(userId);
        return MyPostCommentResponse.from(findMyComment);
    }

    public List<MyVideoCommentResponse> getMyVideoComment(Long userId) {
        List<VideoCommentEntity> findMyComment = videoCommentRepository.findMyComment(userId);
        return MyVideoCommentResponse.from(findMyComment);
    }

    public List<NoticeResponse> getMyNotice(Long userId) { // TODO 수정 필요
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        return noticeRepository.findByUserEntity(UserEntity.fromModel(user)).stream().map(NoticeResponse::new).sorted(Comparator.comparing(NoticeResponse::getCreatedAt).reversed()).toList();
    }
}
