package santa_cruz_alimento_backend.dto.Response;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;

    private String full_name;

    private String ci;

    private String password;

    private Long rol_id;

    private String rol_name;
}
