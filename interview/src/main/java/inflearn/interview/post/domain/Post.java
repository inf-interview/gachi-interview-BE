package inflearn.interview.post.domain;

import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Post {

    private Long id;
    private User user;
    private String title;
    private String content;
    private String tag;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int numOfLike;
    private int numOfComment;

    @Builder
    public Post(Long id, User user, String title, String content, String tag, String category, LocalDateTime createdAt, LocalDateTime updatedAt, int numOfLike, int numOfComment) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.numOfLike = numOfLike;
        this.numOfComment = numOfComment;
    }

    public static Post from(User writer, PostCreate postCreate) {
        return Post.builder()
                .user(writer)
                .title(postCreate.getPostTitle())
                .content(postCreate.getContent())
                .category(postCreate.getCategory())
                .tag(tagConverter(postCreate.getTag()))
                .createdAt(LocalDateTime.now())
                .numOfComment(0)
                .build();
    }

    public Post update(PostUpdate postUpdate) {
        return Post.builder()
                .id(id)
                .user(user)
                .title(postUpdate.getPostTitle())
                .content(postUpdate.getContent())
                .category(postUpdate.getCategory())
                .tag(tagConverter(postUpdate.getTag()))
                .createdAt(createdAt)
                .updatedAt(LocalDateTime.now())
                .numOfComment(numOfComment)
                .build();
    }

    public Post plusLike() {
        return Post.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .category(category)
                .tag(tag)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .numOfLike(numOfLike + 1)
                .numOfComment(numOfComment)
                .build();
    }

    public Post minusLike() {
        return Post.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .category(category)
                .tag(tag)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .numOfLike(numOfLike - 1)
                .numOfComment(numOfComment)
                .build();
    }

    public Post plusComment() {
        return Post.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .category(category)
                .tag(tag)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .numOfLike(numOfLike)
                .numOfComment(numOfComment + 1)
                .build();
    }

    public Post minusComment() {
        return Post.builder()
                .id(id)
                .user(user)
                .title(title)
                .content(content)
                .category(category)
                .tag(tag)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .numOfLike(numOfLike)
                .numOfComment(numOfComment - 1)
                .build();
    }

    private static String tagConverter(String[] tags) {
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
