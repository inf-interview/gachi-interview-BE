package inflearn.interview.controller;

import inflearn.interview.domain.Feedback;
import inflearn.interview.domain.User;
import inflearn.interview.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/{video_Id}")
    public void submit(@RequestBody User user,@RequestParam Long videoId){
        feedbackService.GPTFeedback(videoId, user);
    }

    @DeleteMapping("/{feedbackId}")
    public void delete(@PathVariable Long feedbackId){
        feedbackService.deleteFeedback(feedbackId);
    }

    @GetMapping("/{userId}")
    public List<Feedback> getFeedbacks(@RequestParam Long userId){
        return feedbackService.getFeedbacks(userId);
    }

    @GetMapping("/{feedbackId}")
    public Feedback getFeedback(@PathVariable Long feedbackId){
        return feedbackService.getFeedback(feedbackId);
    }
}