package santa_cruz_alimento_backend.dto.request;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String ci;

    private String password;
}
