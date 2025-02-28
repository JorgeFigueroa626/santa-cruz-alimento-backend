package santa_cruz_alimento_backend.dto.Response;

import lombok.Data;

@Data
public class AuthResponseDto {

    private Long user_id;

    private String rol;

    private String token;
}
