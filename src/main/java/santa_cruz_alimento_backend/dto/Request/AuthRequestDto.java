package santa_cruz_alimento_backend.dto.Request;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String ci;

    private String password;
}
