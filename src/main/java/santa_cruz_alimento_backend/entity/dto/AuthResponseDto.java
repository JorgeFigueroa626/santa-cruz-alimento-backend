package santa_cruz_alimento_backend.entity.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private Long user_id;

    private String rol;

    private String token;
}
