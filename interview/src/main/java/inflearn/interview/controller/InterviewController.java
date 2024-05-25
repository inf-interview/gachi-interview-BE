package inflearn.interview.controller;

import inflearn.interview.domain.Video;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
    /**
     * 영상 녹화 완료
     */
    private final VideoRepository videoRepository;
    @PostMapping
    @Transactional
    public void complete(@RequestBody Video video){
        videoRepository.save(video);
    }
}