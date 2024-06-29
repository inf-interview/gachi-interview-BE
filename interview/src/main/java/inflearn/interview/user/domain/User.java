package inflearn.interview.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public class User implements UserDetails {

    private Long id;
    private String name;
    private String email;
    private String social;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String image;
    private String refreshToken;
    private String role;
    private Integer questionGptCallCount;
    private Integer interviewGptCallCount;
    private LocalDateTime questionGptCallTime;
    private LocalDateTime interviewGptCallTime;

    @Builder
    public User(Long id, String name, String email, String social, LocalDateTime createdAt, LocalDateTime updatedAt, String image, String refreshToken, String role, Integer questionGptCallCount, Integer interviewGptCallCount, LocalDateTime questionGptCallTime, LocalDateTime interviewGptCallTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.social = social;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.image = image;
        this.refreshToken = refreshToken;
        this.role = role;
        this.questionGptCallCount = questionGptCallCount;
        this.interviewGptCallCount = interviewGptCallCount;
        this.questionGptCallTime = questionGptCallTime;
        this.interviewGptCallTime = interviewGptCallTime;
    }

    public static User from(UserCreate userCreate) {
        return User.builder()
                .name(userCreate.getName())
                .email(userCreate.getEmail())
                .social(userCreate.getSocial())
                .createdAt(LocalDateTime.now())
                .image(userCreate.getImage())
                .role(userCreate.getRole())
                .interviewGptCallCount(0)
                .questionGptCallCount(0)
                .build();
    }

    public User setRefreshToken(String refreshToken) {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(questionGptCallCount)
                .interviewGptCallCount(interviewGptCallCount)
                .questionGptCallTime(questionGptCallTime)
                .interviewGptCallTime(interviewGptCallTime)
                .build();
    }

    /**
     * GPT
     */

    public User setQuestionCallCountOne() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(1)
                .interviewGptCallCount(interviewGptCallCount)
                .questionGptCallTime(LocalDateTime.now())
                .interviewGptCallTime(interviewGptCallTime)
                .build();
    }

    public User setQuestionCallCountZero() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(0)
                .interviewGptCallCount(interviewGptCallCount)
                .questionGptCallTime(questionGptCallTime)
                .interviewGptCallTime(interviewGptCallTime)
                .build();
    }

    public User setInterviewCallCountOne() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(questionGptCallCount)
                .interviewGptCallCount(1)
                .questionGptCallTime(questionGptCallTime)
                .interviewGptCallTime(LocalDateTime.now())
                .build();
    }

    public User setInterviewCallCountZero() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(questionGptCallCount)
                .interviewGptCallCount(0)
                .questionGptCallTime(questionGptCallTime)
                .interviewGptCallTime(interviewGptCallTime)
                .build();
    }

    public User plusQuestionCall() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(questionGptCallCount + 1)
                .interviewGptCallCount(interviewGptCallCount)
                .questionGptCallTime(questionGptCallTime)
                .interviewGptCallTime(interviewGptCallTime)
                .build();
    }

    public User plusInterviewCall() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(questionGptCallCount)
                .interviewGptCallCount(interviewGptCallCount + 1)
                .questionGptCallTime(questionGptCallTime)
                .interviewGptCallTime(interviewGptCallTime)
                .build();
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
