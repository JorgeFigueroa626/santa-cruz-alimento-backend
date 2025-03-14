package santa_cruz_alimento_backend.service.interfaces;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFirebaseStorageService {

    String uploadImage(String container, MultipartFile file) throws IOException;
}
