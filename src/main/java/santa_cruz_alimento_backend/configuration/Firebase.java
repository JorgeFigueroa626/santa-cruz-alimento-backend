package santa_cruz_alimento_backend.configuration;

import org.springframework.context.annotation.Configuration;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class Firebase {

    @Value("${firebase.storage.account.key}")
    private String FIREBASE_ACCOUNT_KEY;

    @Value("${firebase.storage.file.path.base}")
    private String FIREBASE_STORAGE;


    @PostConstruct
    public void init() throws IOException, ExceptionNotFoundException {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(FIREBASE_ACCOUNT_KEY);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(FIREBASE_STORAGE)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }catch (IOException e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
