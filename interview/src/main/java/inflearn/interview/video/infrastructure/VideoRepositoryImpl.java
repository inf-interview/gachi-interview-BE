package inflearn.interview.video.infrastructure;

import inflearn.interview.video.domain.Video;
import inflearn.interview.video.service.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VideoRepositoryImpl implements VideoRepository {

    private final VideoJpaRepository videoJpaRepository;

    @Override
    public Optional<Video> findById(Long id) {
        return videoJpaRepository.findById(id).map(VideoEntity::toModel);
    }

    @Override
    public Video save(Video video) {
        return videoJpaRepository.save(VideoEntity.fromModel(video)).toModel();
    }

    @Override
    public void delete(Video video) {
        videoJpaRepository.delete(VideoEntity.fromModel(video));
    }
}
