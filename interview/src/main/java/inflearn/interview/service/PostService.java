package inflearn.interview.service;

import inflearn.interview.domain.Post;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시글 생성
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).get();
    }

    public Long createPost(PostDTO postDTO) {
        Post post = new Post(postDTO.getPostTitle(), postDTO.getContent(), DtoToEntityTagConverter(postDTO.getTag()), postDTO.getCategory());
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

    public void likePost(Long postId) {
        Post findPost = postRepository.findById(postId).get();

        //유저 정보, postId를 이용하여 이미 like가 있는지 확인

        //없을 경우 새로 생성 -> 좋아요 생성
        //있을 경우 삭제 -> 좋아요 취소
    }

    private String DtoToEntityTagConverter(String[] tags) {
        StringBuilder tagMaker = new StringBuilder();
        for (String tag : tags) {
            tagMaker.append(tag).append(".");
        }
        return tagMaker.toString();
    }
}
