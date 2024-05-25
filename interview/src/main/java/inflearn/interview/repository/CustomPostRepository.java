package inflearn.interview.repository;

import inflearn.interview.domain.dto.PageInfo;
import inflearn.interview.domain.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<PostDTO> findAllPostByPageInfo(PageInfo pageInfo, Pageable pageable);
}
