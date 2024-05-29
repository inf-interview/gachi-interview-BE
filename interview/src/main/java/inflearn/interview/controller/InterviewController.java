package inflearn.interview.controller;

import inflearn.interview.domain.Video;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
@Slf4j
public class InterviewController {
    /**
     * 영상 녹화 완료
     */
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    @PostMapping
    @Transactional
    public Long complete(@RequestBody Video video){
        video.setUser(userRepository.findById(video.getUser().getUserId()).get());
        video.setTime(LocalDateTime.now());
        String tag = "";
        for (int i = 0; i < video.getTags().size(); i++) {
            tag += video.getTags().get(i)+".";
        }
        tag = tag.substring(0, tag.length()-1);
        video.setTag(tag);
        Video saved = videoRepository.save(video);
        return saved.getVideoId();
    }
}