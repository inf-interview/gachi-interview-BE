package inflearn.interview.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import inflearn.interview.domain.FCM;
import inflearn.interview.repository.FCMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FcmTokenService {

    private final FCMRepository fcmRepository;

    public void registerToken(Long userId, String token) {
        FCM fcm = new FCM();
        fcm.setUserId(userId);
        fcm.setToken(token);
        fcmRepository.save(fcm);
    }

    private String getTokenByUserId(Long userId) {
        return fcmRepository.findByUserId(userId)
                .map(FCM::getToken)
                .orElse(null);
    }

    public void commentSendNotification(Long userId, String titleName, String commentUser) {
        String token = getTokenByUserId(userId);

        String title = titleName+"에 새로운 댓글이 작성되었습니다.";
        String body = commentUser+"님이 "+titleName+"에 댓글을 작성했습니다.";

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
}