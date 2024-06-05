package inflearn.interview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.domain.Feedback;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.TempDTO;
import inflearn.interview.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/{video_Id}")
    public void submit(@AuthenticationPrincipal User user, @PathVariable Long video_Id) throws JsonProcessingException {
        feedbackService.GPTFeedback(video_Id, user);
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