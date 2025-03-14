package santa_cruz_alimento_backend.service.implementacion;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import santa_cruz_alimento_backend.service.interfaces.IFirebaseStorageService;
import santa_cruz_alimento_backend.util.shared.Container;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService implements IFirebaseStorageService {

    @Value("${firebase.storage.url}")
    private String FIREBASE_STORAGE_URL;

    @Value("${firebase.storage.file.path.base}")
    private String FIREBASE_STORAGE;

    private static final String BUCKET_NAME = "web-ecommerce-dcdaa.appspot.com";
    @Override
    public String uploadImage(String container, MultipartFile file) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket(FIREBASE_STORAGE);

        // Genera name unico para la image
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        //String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Subir image al bucker o Url
        Blob blob = bucket.create(container + "/" + fileName, file.getBytes(), file.getContentType());

        // Obtener la URL de la Image
        return FIREBASE_STORAGE_URL + FIREBASE_STORAGE + "/o/" +
                blob.getName().replace("/", "%2F") + "?alt=media";
    }
}
