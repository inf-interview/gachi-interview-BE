package inflearn.interview.controller;

import inflearn.interview.domain.Video;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
    /**
     * 영상 녹화 완료
     */
    private final VideoRepository videoRepository;
    @GetMapping
    @Transactional
    public Long complete(@RequestBody Video video){
        Video saved = videoRepository.save(video);
        return saved.getVideoId();
    }
}