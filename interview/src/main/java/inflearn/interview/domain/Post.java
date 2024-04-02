package inflearn.interview.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    Long postId;
    String title;
    String content;
    String tag;
}