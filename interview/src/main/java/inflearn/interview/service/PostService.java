package inflearn.interview.service;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.PostLike;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.LikeDTO;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.exception.RequestDeniedException;
import inflearn.interview.repository.PostLikeRepository;
import inflearn.interview.repository.PostRepository;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;


    public Page<PostDTO> getAllPost(String sortType, String category, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return postRepository.findAllPostByPageInfo(sortType, category, pageRequest);
    }

    public PostDTO getPostById(Long postId, Long userId) {
        PostDTO postDTO = postRepository.findPostByPostId(postId);
        Optional<PostLike> postLike = postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId);
        if (postLike.isEmpty()) {
            postDTO.setLiked(false);
        } else {
            postDTO.setLiked(true);
        }
        return postDTO;
    }
    //게시글 생성

    public PostDTO createPost(PostDTO postDTO) {
        User findUser = userRepository.findById(postDTO.getUserId()).get();
        Post post = new Post(findUser, postDTO.getPostTitle(), postDTO.getContent(), DtoToEntityTagConverter(postDTO.getTag()), postDTO.getCategory());
        postRepository.save(post);
        postDTO.setUserId(findUser.getUserId());
        postDTO.setPostId(post.getPostId());
        postDTO.setTime(post.getCreatedAt());
        return postDTO;
    }

    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post findPost = postRepository.findById(postId).get();
        if (postDTO.getUserId().equals(findPost.getUser().getUserId())) {
            findPost.setTitle(postDTO.getPostTitle());
            findPost.setCategory(postDTO.getCategory());
            findPost.setTag(DtoToEntityTagConverter(postDTO.getTag()));
            findPost.setContent(postDTO.getContent());
            postDTO.setPostId(findPost.getPostId());
            return postDTO;
        } else {
            throw new RequestDeniedException();
        }
    }

    public void deletePost(Long postId, Long userId) {
        //유저가 작성한 포스트인지 체크
        Post post = postRepository.findById(postId).get();
        log.info("userId {}", post.getUser().getUserId());

        if (!(post.getUser().getUserId()).equals(userId)) {
            throw new RequestDeniedException();
        }
        postRepository.deleteById(postId);
    }

    public LikeDTO likePost(Long postId, Long userId) {
        Post findPost = postRepository.findById(postId).get();
        User user = userRepository.findById(userId).get();

        Optional<PostLike> findPostLike = postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId);

        if (findPostLike.isEmpty()) {
            //없으므로 새로 생성
            PostLike postLike = new PostLike(findPost, user);
            postLikeRepository.save(postLike);
            findPost.setNumOfLike(findPost.getNumOfLike() + 1);
            return new LikeDTO(findPost.getNumOfLike(), true);
        } else {
            //있던것 삭제
            postLikeRepository.delete(findPostLike.get());
            findPost.setNumOfLike(findPost.getNumOfLike() - 1);
            return new LikeDTO(findPost.getNumOfLike(), false);
        }



        //유저 정보, postId를 이용하여 이미 like가 있는지 확인

        //없을 경우 새로 생성 -> 좋아요 생성
        //있을 경우 삭제 -> 좋아요 취소
    }

    private String DtoToEntityTagConverter(String[] tags) {
        if (tags != null) {
            StringBuilder tagMaker = new StringBuilder();
            for (String tag : tags) {
                tagMaker.append(tag).append(".");
            }
            return tagMaker.toString();
        } else {
            return null;
        }
    }
}
