package santa_cruz_alimento_backend.dto.request.auth;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String ci;

    private String password;
}
