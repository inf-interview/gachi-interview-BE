package inflearn.interview.user.service;

import inflearn.interview.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByEmailAndSocial(String email, String social);

    User save(User user);

    Optional<User> findById(Long id);

    User findWriter(Long postId);
}
