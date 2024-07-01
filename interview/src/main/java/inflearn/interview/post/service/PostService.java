package inflearn.interview.post.service;

import inflearn.interview.post.controller.response.MyPostResponse;
import inflearn.interview.post.controller.response.PostDetailResponse;
import inflearn.interview.post.domain.PostCreate;
import inflearn.interview.post.domain.Post;
import inflearn.interview.post.controller.response.PostResponse;
import inflearn.interview.post.domain.PostDelete;
import inflearn.interview.post.domain.PostUpdate;
import inflearn.interview.postcomment.service.PostCommentRepository;
import inflearn.interview.postlike.controller.response.LikeResponse;
import inflearn.interview.postlike.domain.PostLike;
import inflearn.interview.postlike.domain.PostLikeRequest;
import inflearn.interview.user.domain.User;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.postlike.service.PostLikeRepository;
import inflearn.interview.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final CustomPostRepository customPostRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    private Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(OptionalNotFoundException::new);
    }

    public Page<PostResponse> getAllPost(String sortType, String category, String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 12);
        return customPostRepository.findAllPostByPageInfo(sortType, category, keyword, pageRequest);
    }

    public PostDetailResponse getPostDetail(Long postId, Long userId) {
        Post post = getById(postId);
        User writer = userRepository.findPostWriter(postId);
        int numOfComments = postCommentRepository.getCommentCount(postId);
        Optional<PostLike> postLike = postLikeRepository.findPostLike(userId, postId);
        if (postLike.isEmpty()) {
            return PostDetailResponse.from(writer, post, numOfComments, false);
        }
        return PostDetailResponse.from(writer, post, numOfComments, true);
    }
    //게시글 생성

    public Post create(PostCreate postCreate) {
        User user = userRepository.findById(postCreate.getUserId()).orElseThrow(OptionalNotFoundException::new);
        Post post = Post.from(user, postCreate);
        return postRepository.save(post);
    }

    public Post update(Long postId, PostUpdate postUpdate) {
        Post post = getById(postId);
        post = post.update(postUpdate);
        return postRepository.save(post);
    }

    public void delete(PostDelete postDelete) {
        Post post = getById(postDelete.getPostId());
        postRepository.delete(post);
    }

    public LikeResponse likePost(PostLikeRequest postLikeRequest) { // like 처리는 그대로 사용
        Post post = getById(postLikeRequest.getPostId());
        User user = userRepository.findById(postLikeRequest.getUserId()).orElseThrow(OptionalNotFoundException::new);

        Optional<PostLike> postLike = postLikeRepository.findPostLike(postLikeRequest.getUserId(), postLikeRequest.getPostId());

        if (postLike.isEmpty()) {
            PostLike like = PostLike.from(post, user);
            postLikeRepository.save(like);
            Post likedPost = post.plusLike();
            postRepository.save(likedPost);

            return LikeResponse.from(true, likedPost);
        } else {
            postLikeRepository.delete(postLike.get());
            Post unLikedPost = post.minusLike();
            postRepository.save(unLikedPost);

            return LikeResponse.from(false, unLikedPost);
        }

    }

    public List<MyPostResponse> getMyPost(Long userId, String category) {
        return postRepository.findMyPost(userId, category);
    }
}
