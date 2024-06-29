package inflearn.interview.user.service;

import inflearn.interview.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByEmailAndSocial(String email, String social);

    User save(User user);

    Optional<User> findById(Long id);

    User findPostWriter(Long postId);

    User findVideoWriter(Long videoId);

    Optional<User> findAdmin(String role);
}
