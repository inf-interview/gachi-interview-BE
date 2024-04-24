package inflearn.interview.service;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.PostLike;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.PageInfo;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.repository.PostLikeRepository;
import inflearn.interview.repository.PostRepository;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;


    public Page<PostDTO> getAllPost(PageInfo pageInfo, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return postRepository.findAllPostByPageInfo(pageInfo, pageRequest);
    }

    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return new PostDTO(post);
    }
    //게시글 생성

    public Long createPost(PostDTO postDTO) {
        User findUser = userRepository.findById(postDTO.getUserId()).get();
        Post post = new Post(findUser, postDTO.getPostTitle(), postDTO.getContent(), DtoToEntityTagConverter(postDTO.getTag()), postDTO.getCategory());
        postRepository.save(post);
        return post.getPostId();
    }

    public void updatePost(Long postId, PostDTO postDTO) {
        Post findPost = postRepository.findById(postId).get();
        findPost.setTitle(postDTO.getPostTitle());
        findPost.setCategory(postDTO.getCategory());
        findPost.setTag(DtoToEntityTagConverter(postDTO.getTag()));
        findPost.setContent(postDTO.getContent());
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void likePost(Long postId, Long userId) {
        Post findPost = postRepository.findById(postId).get();
        User user = userRepository.findById(userId).get();

        Optional<PostLike> findPostLike = postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId);

        if (findPostLike.isEmpty()) {
            //없으므로 새로 생성
            PostLike postLike = new PostLike(findPost, user);
            postLikeRepository.save(postLike);
            findPost.setNumOfLike(findPost.getNumOfLike() + 1);
        } else {
            //있던것 삭제
            postLikeRepository.delete(findPostLike.get());
            findPost.setNumOfLike(findPost.getNumOfLike() - 1);
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
