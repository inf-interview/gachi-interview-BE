package inflearn.interview.post.service;

import inflearn.interview.post.domain.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<PostDTO> findAllPostByPageInfo(String sortType, String category, String keyword, Pageable pageable);
}
