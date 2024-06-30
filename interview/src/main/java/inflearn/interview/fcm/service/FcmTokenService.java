package inflearn.interview.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import inflearn.interview.fcm.domain.Fcm;
import inflearn.interview.notice.infrastructure.Notice;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.notice.infrastructure.NoticeRepository;
import inflearn.interview.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FcmTokenService {

    private final FcmRepository fcmRepository;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    public void registerToken(Long userId, String token) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        Optional<Fcm> getFcm = fcmRepository.findByUserId(userId);
        if (getFcm.isEmpty()) {
            Fcm fcm = Fcm.from(token, user);
            fcmRepository.save(fcm);
        } else {
            Fcm fcm = getFcm.get();
            fcm = fcm.update(token);
            fcmRepository.save(fcm);
        }
    }

    private String getTokenByUserId(Long userId) {
        return fcmRepository.findByUserId(userId)
                .map(Fcm::getFcmToken)
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void commentSendNotification(Long userId, String titleName, String commentUser) {
        String token = getTokenByUserId(userId);

        String title = titleName+"에 새로운 댓글이 작성되었습니다.";
        String body = commentUser+"님이 "+titleName+"에 댓글을 작성했습니다.";
        String sendBody = body +"("+userId+")";

        sendNotification(token, title, sendBody);

        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        Notice notice = new Notice(UserEntity.fromModel(user), body);
        noticeRepository.save(notice);
    }

    private void sendNotification(String token, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void feedbackSendNotification(Long userId, String videoTitle) {
        String token = getTokenByUserId(userId);

        String title = videoTitle+"의 피드백이 완료되었습니다.";
        String body = videoTitle+"의 피드백이 완료되었습니다.";

        sendNotification(token, title, body);
    }
}