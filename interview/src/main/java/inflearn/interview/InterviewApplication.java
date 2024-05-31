package inflearn.interview;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.FileInputStream;
import java.io.IOException;

=======

@SpringBootApplication
@EnableAsync
public class InterviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewApplication.class, args);
	}


	@PostConstruct
	public void initFirebase() throws IOException {
		FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-adminsdk.json");

		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		if (FirebaseApp.getApps().isEmpty()) { // 기본 앱이 초기화되지 않은 경우
			FirebaseApp.initializeApp(options);
		}
	}

}