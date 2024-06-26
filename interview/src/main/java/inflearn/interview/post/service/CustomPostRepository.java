package inflearn.interview.post.service;

import inflearn.interview.post.controller.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {
    Page<PostResponse> findAllPostByPageInfo(String sortType, String category, String keyword, Pageable pageable);
}
