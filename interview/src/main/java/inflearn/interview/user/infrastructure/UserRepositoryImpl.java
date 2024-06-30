package inflearn.interview.user.infrastructure;

import inflearn.interview.user.domain.User;
import inflearn.interview.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findUserByEmailAndSocial(String email, String social) {
        return userJpaRepository.findUserByEmailAndSocial(email, social).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }

    @Override
    public User findPostWriter(Long postId) {
        return userJpaRepository.findPostWriter(postId).toModel();
    }

    @Override
    public User findVideoWriter(Long videoId) {
        return userJpaRepository.findVideoWriter(videoId).toModel();
    }

    @Override
    public Optional<User> findAdmin(String role) {
        return userJpaRepository.findAdmin(role).map(UserEntity::toModel);
    }
}